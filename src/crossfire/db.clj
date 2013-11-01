(ns crossfire.db
  (:require [monger.core :as mg]
            [monger.collection :as coll])
  (:import [org.bson.types ObjectId]))

(mg/connect! )
(def db (mg/set-db! (mg/get-db "kcl")))

(defn insert [doc]
  (let [oid (ObjectId.)]
    (insert db (merge doc {:_id oid}))))

(defn insert-user [username userdata]
  (coll/insert "users" {username userdata :_id (ObjectId.)}))

(defn insert-clan [clan-name players extra]
  (let [players (map #(if (= % "") nil %) players)]
   (coll/insert "clans" {:name clan-name :players players :extra extra})))

(defn get-clans []
  (coll/find-maps "clans"))

(defn update-user [username userdata]
  (let [id (some #(when (= username (-> % (dissoc :_id) vals first :username))
                    (:_id %))
                 (coll/find-maps "users"))]
    (coll/update-by-id "users" id userdata)
    userdata))

(defn update-clan [clan players extra]
  (coll/update-by-id "clans" (:_id clan) {:name (:name clan) :players players :extra extra}))

(defn get-users []
  (reduce (fn [sum item]
            (merge sum (let [user (-> item (dissoc :_id) vals first)]
                         {(:username user) user})))
          {}
          (coll/find-maps "users")))

(defn get-usernames []
  (map :username (vals (get-users))))

(defn get-user [username]
  ((get-users) username))

(defn clan-of-username [username]
  (coll/find-one-as-map "clans" {:players username}))
