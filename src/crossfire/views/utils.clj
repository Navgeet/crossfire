(ns crossfire.views.utils)

(defn link [link-to text]
  (format "<a href=\"%s\">%s</a>" link-to text))
