(ns crossfire.views
  (:require [clojure.java.io :as io]
            [crossfire.views.header]
            [crossfire.views.footer]
            [crossfire.views.navbar]))

(defn- html-for [view common-data args]
  (apply (ns-resolve (symbol (str "crossfire.views." view)) 'view) common-data args))

(defn view [view common-data args]
  (require (symbol (str "crossfire.views." view)))
  (let [js-uri (io/resource (format "public/js/%s.js" view))]
    (str (html-for "header" common-data (into [view] args))
         (html-for "navbar" common-data args)
         (html-for view common-data args)
         (when js-uri
          (format "<script src=\"/js/%s.js\"></script>" view))
         (html-for "footer" common-data args))))
