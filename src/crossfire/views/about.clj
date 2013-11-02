(ns crossfire.views.about
  (:require [clojure.java.io :as io]))

(defn view [common-data]
  (format "<div class=\"container\">
        <div class=\"page-header\">
          <h1>About KCL</h1>%s</div>
      </div>
    </div>" (slurp (io/resource "public/kcl.txt"))))
