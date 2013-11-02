(defproject crossfire "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.1"]
                 [com.cemerick/friend "0.2.0"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [com.novemberain/monger "1.7.0-beta1"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler crossfire.handler/app}
  :min-lein-version "2.2.0"
  :main crossfire.handler
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
