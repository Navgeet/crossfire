(ns crossfire.views.account)

(defn view [{:keys [username name email phone]} & args]
  (format "<div class=\"container\">
      <form action=\"account\" method=\"POST\" class=\"form-signin\">
        <h2 class=\"form-signin-heading\">Update details</h2>
        <input value=\"%s\" name=\"name\" type=\"text\" class=\"form-control\" placeholder=\"Full name (will not be shown)\">
        <input value=\"%s\" name=\"email\" type=\"text\" class=\"form-control\" placeholder=\"Email ID (please use a valid one)\">
        <input value=\"%s\" name=\"phone\" type=\"text\" class=\"form-control\" placeholder=\"Phone no.\">
        <input value=\"%s\" name=\"username\" type=\"text\" class=\"form-control\" placeholder=\"The nick you want to use\">
        <input name=\"new-password\" type=\"password\" class=\"form-control\" placeholder=\"Type new password\">
        <input name=\"confirm\" type=\"password\" class=\"form-control\" placeholder=\"Confirm new password\">
        <input name=\"old-password\" type=\"password\" class=\"form-control\" placeholder=\"Verify old password (required)\" required autofocus>
        <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Update</button>
      </form>
</div>
" name email phone username))
