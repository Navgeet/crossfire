(ns crossfire.views.root)

(defn view [_ user]
  (format "      <div class=\"container\">
        <div class=\"page-header\">
          <h1>Hello, %s!</h1>
        </div>
        <p class=\"lead\"></p>
      </div>
    </div>
" user))
