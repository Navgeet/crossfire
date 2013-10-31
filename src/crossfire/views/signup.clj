(ns crossfire.views.signup)

(defn view [_]
  "<div class=\"container\">
      <form action=\"signup\" method=\"POST\" class=\"form-signin\">
        <h2 class=\"form-signin-heading\">Sign Up</h2>
        <input name=\"name\" type=\"text\" class=\"form-control\" placeholder=\"Full name (will not be shown)\" required autofocus>
        <input name=\"email\" type=\"text\" class=\"form-control\" placeholder=\"Email ID (please use a valid one)\" required autofocus>
        <input name=\"phone\" type=\"text\" class=\"form-control\" placeholder=\"Phone no.\" required autofocus>
        <input name=\"username\" type=\"text\" class=\"form-control\" placeholder=\"The nick you want to use\" required autofocus>
        <input name=\"password\" type=\"password\" class=\"form-control\" placeholder=\"Password\" required>
        <input name=\"confirm\" type=\"password\" class=\"form-control\" placeholder=\"Confirm password\" required>
        <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Sign Up</button>
      </form>
</div>
")
