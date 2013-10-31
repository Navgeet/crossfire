(ns crossfire.views.navbar
  (:require [crossfire.views.utils :as utils]))

(defn view [user & args]
  (format "<body>
    <div id=\"wrap\">
      <div class=\"navbar navbar-default navbar-fixed-top\">
        <div class=\"container\">
          <div class=\"navbar-header\">
            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">
              <span class=\"icon-bar\"></span>
              <span class=\"icon-bar\"></span>
              <span class=\"icon-bar\"></span>
            </button>
            <a class=\"navbar-brand\" href=\"/\">KCL</a>
          </div>
          <div class=\"collapse navbar-collapse\">
            <ul class=\"nav navbar-nav\">
              <li class=\"active\"><a href=\"#\">Home</a></li>
              <li>%s</li>
              <li>%s</li>
              <li class=\"dropdown\">
                <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">%s<b class=\"caret\"></b></a>
                <ul class=\"dropdown-menu\">
                  <li>%s</li>
                  <li>%s</li>
                  <li class=\"divider\"></li>
                  <li>%s</li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </div>
"
          (utils/link "/clans" "Clans")
          (utils/link "/users" "Users")
          (if user (str "Hello, " (:username user)) "New Here?")
          (if user (utils/link "/logout" "Logout") (utils/link "/login" "Login"))
          (if user (utils/link "/account" "Your account") (utils/link "/signup" "Sign Up"))
          (utils/link "/faq" "FAQ")))
