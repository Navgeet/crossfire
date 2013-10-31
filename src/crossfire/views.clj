(ns crossfire.views
  (:require [crossfire.views.header]
            [crossfire.views.footer]
            [crossfire.views.navbar]))

(defn- html-for [view common-data args]
  (apply (ns-resolve (symbol (str "crossfire.views." view)) 'view) common-data args))

(defn view [view common-data args]
  (require (symbol (str "crossfire.views." view)))
  (str (html-for "header" common-data (into [view] args))
       (html-for "navbar" common-data args)
       (html-for view common-data args)
       (html-for "footer" common-data args)))
