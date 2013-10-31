(ns crossfire.views.users)

(defn view [_ usernames & args]
  (format "<div class=\"container\">
        <div class=\"page-header\">
          <h1>All users</h1>
        </div>
        %s
      </div>
    </div>
" (reduce (fn [sum item]
            (str sum (format "<p class=\"small\">%s</p>" item)))
          "" usernames)))
