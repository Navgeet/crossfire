(ns crossfire.views.header
  (:require [crossfire.views.utils :as utils]))

(defn view [_ page & args]
  (format "<!DOCTYPE html>
<html lang=\"en\">
  <head>
    <meta charset=\"utf-8\">
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
    <meta name=\"description\" content=\"\">
    <meta name=\"author\" content=\"\">
    <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/img/favicon.ico\">
    <title>KCL</title>
    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">
    <link href=\"/css/root.css\" rel=\"stylesheet\">%s
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src=\"../../assets/js/html5shiv.js\"></script>
      <script src=\"../../assets/js/respond.min.js\"></script>
    <![endif]-->
  </head>" (if (= page "root") ""
               (format "<link href=\"/css/%s.css\" rel=\"stylesheet\">" page))))
