(ns crossfire.handler
  (:use compojure.core)
  (:require [crossfire.views :as views]
            [crossfire.misc :as misc]
            [crossfire.db :as db]
            [cemerick.friend :as friend]
            [cemerick.friend.credentials :refer (hash-bcrypt)]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [ring.util.response :as resp]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :as h]
            [clojure.string :as str]))

(derive ::admin ::user)

(defn- create-user
  [{:keys [username password name email phone] :as user-data}]
  (-> (dissoc user-data :admin)
      (assoc :identity username
             :password (creds/hash-bcrypt password)
             :roles (into #{::user} (when nil [::admin])))))

(defn check-pass-for-user [user pass]
  (= (:password user) (creds/hash-bcrypt pass)))

(defn- update-user
  [current-user {:keys [username old-password new-password name email phone] :as user-data}]
  (if (check-pass-for-user current-user old-password)
    (db/update-user (:username current-user)
                    (merge current-user
                           (when username {:identity username :username username})
                           (when name {:name name})
                           (when email {:email email})
                           (when phone {:phone phone})
                           (when new-password {:password (creds/hash-bcrypt new-password)})))
    nil))

(defn- route-with-common-data [view req & args]
  (views/view view (friend/current-authentication) args))

(defroutes app-routes
  (GET "/" req (route-with-common-data "root" req))
  (GET "/login" req (route-with-common-data "login" req))
  (GET "/logout" req (friend/logout* (resp/redirect (str (:context req) "/")) ))
  (GET "/signup" req (route-with-common-data "signup" req))
  (POST "/signup" {{:keys [username password confirm] :as params} :params :as req}
        (if (and (not-any? str/blank? [username password confirm])
                 (= password confirm))
          (let [user (create-user (select-keys params [:username :password :name :email :phone]))]
            (db/insert-user username user)
            (friend/merge-authentication
             (resp/redirect (str (:context req) "/")
                            ;;(misc/context-uri req username)
                            )
             user))
          (assoc (resp/redirect (str (:context req) "/")) :flash "Passwords didn't match!")))

  (GET "/account" req (friend/authenticated (route-with-common-data "account" req)))

  (POST "/account" {{:keys [old-password new-password confirm] :as params} :params :as req}
        (if (= new-password confirm)
          (if-let [user (update-user (friend/current-authentication)
                                     (select-keys params [:username :old-password :new-password :name :email :phone]))]
            (friend/merge-authentication (resp/redirect (str (:context req) "/")) user)
            (assoc (resp/redirect (str (:context req) "/account")) :flash "Verify your previous password"))
          (assoc (resp/redirect (str (:context req) "/")) :flash "Passwords didn't match!")))

  (GET "/user/:user" req
       (friend/authenticated
        (let [user (:user (req :params))]
          (if (= user (:username (friend/current-authentication)))
            (route-with-common-data "user" req user)
            (assoc (resp/redirect (str (:context req) "/")) :flash "You are not authorized here!")))))
  (GET "/users" req (route-with-common-data "users" req (db/get-usernames)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site (friend/authenticate app-routes {:allow-anon? true
                                                 :login-uri "/login"
                                                 :default-landing-uri "/"
                                                 :unauthorized-handler #(-> (h/html5 [:h2 "You do not have sufficient privileges to access " (:uri %)])
                                                                            resp/response
                                                                            (resp/status 401))
                                                 :credential-fn #(creds/bcrypt-credential-fn (db/get-users) %)
                                                 :workflows [(workflows/interactive-form)]})))
