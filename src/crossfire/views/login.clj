(ns crossfire.views.login)

(def extra-css "login.css")

(defn view [_ & args]
  "<div class=\"container\">
      <form action=\"login\" method=\"POST\" class=\"form-signin\">
        <h2 class=\"form-signin-heading\">Login</h2>
        <input name=\"username\" type=\"text\" class=\"form-control\" placeholder=\"Username\" required autofocus>
        <input name=\"password\" type=\"password\" class=\"form-control\" placeholder=\"Password\" required>
        <label class=\"checkbox\">
          <input type=\"checkbox\" value=\"remember-me\"> Remember me
        </label>
        <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Login</button>
      </form>
</div>
")
