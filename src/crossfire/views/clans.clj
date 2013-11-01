(ns crossfire.views.clans
  (:require [clojure.set :refer [difference]]))

(comment (defn view [common-data]
           "      <div class=\"container\">
<h1>Available players</h1>
</div class=\"well\">
<span draggable=\"true\" class=\"label label-primary\">abc</span>
<span draggable=\"true\" class=\"label label-primary\">pqr</span>
<span id=\"nav\" draggable=\"true\" ondragstart=\"dragIt(this, event)\" class=\"label label-primary\">xyz</span>
</div>
<div class=\"well\">
<table>
<tr><td width=\"100\" ondrop=\"dropIt(this, event)\" ondragenter=\"return false\" ondragover=\"return false\">           </td></tr>
<tr><td width=\"100\" ondrop=\"dropIt(this, event)\" ondragenter=\"return false\" ondragover=\"return false\">           </td></tr>
<tr><td width=\"100\" ondrop=\"dropIt(this, event)\" ondragenter=\"return false\" ondragover=\"return false\">           </td></tr>
</table>
</div>
</div>
</div>
"))

(comment (defn view [common-data]
   "      <div class=\"container\">
<h2>Available players:</h1>
<div class=\"well\" ondragenter=\"return false\" ondrop=\"handleDrop(this, event)\" ondragover=\"handleDragOver(this, event)\">
<span draggable=\"true\" class=\"label label-primary\">abc</span>
<span draggable=\"true\" class=\"label label-primary\">pqr</span>
<span id=\"nav\" draggable=\"true\" ondragend=\"handleDragEnd(this, event)\" ondragstart=\"handleDragStart(this, event) \"class=\"label label-primary\">xyz</span>
</div><!-- well -->
<h2>Current Clans:</h1>
<div class=\"well\">
<div class=\"row\">
<div class=\"col-md-4\">
<div class=\"second-well\">
<li ondragenter=\"return false\" ondrop=\"handleDrop(this, event)\" ondragover=\"handleDragOver(this, event)\" class=\"list-group-item\">
<!--
<span draggable=\"true\" ondragend=\"handleDragEnd(this, event)\"  ondragstart=\"handleDragStart(this, event)\" class=\"label label-primary\">foo</span> -->
</li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<small>6th-player</small>
<li class=\"list-group-item\"></li>
</div></div>
<div class=\"col-md-4\">
<div class=\"second-well\">
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<small>6th-player</small>
<li class=\"list-group-item\"></li>
</div></div>
<div class=\"col-md-4\">
<div class=\"second-well\">
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<li class=\"list-group-item\"></li>
<small>6th-player</small>
<li class=\"list-group-item\"></li>
</div></div>
</div>

</div>


      </div>
    </div>
"))

(defn row-for-id-with-val [id val]
  (format "<div class=\"row\">
<div class=\"col-md-10\">
<input value=\"%s\" id=\"pl%d\" name=\"p%d\" type=\"text\" class=\"form-control\" placeholder=\"%s\" readonly>
</div>
<div class=\"col-md-2\">
<button type=\"button\" class=\"btn btn-default\" onclick=\"
var val = document.getElementById('pl%d').value;
var div = document.createElement('div');
div.innerHTML = '<button type=\\'button\\' class=\\'btn btn-default\\' onclick=\\'button_press(this)\\'>'
+ val + '</button>';
document.getElementById('pl%d').value='';
document.getElementById('players').appendChild(div);
\">clear</button>
</div>
</div>" val id id (if-not (= id 6) (str "Player " id)
                      "Reserved player") id id))

(defn form-my-clan [username]
  (str "<form action=\"clan-form\" method=\"POST\" class=\"form-signin\">
<h2 class=\"form-signin-heading\">Form your clan</h2>"
       "<div class=\"row\">
<div class=\"col-md-10\">
<input name=\"clan-name\" type=\"text\" class=\"form-control\" placeholder=\"Clan name\" required autofocus>
</div>
</div>"
       (row-for-id-with-val 1 username)
       (apply str (for [id (range 1 6)]
                    (row-for-id-with-val (+ 1 id) "")))
       "<div class=\"row\"><div class=\"col-md-10\">
<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Submit</button></div></div>
      </form>
"))

(defn update-my-clan [players extra]
  (str "<form action=\"clan-update\" method=\"POST\" class=\"form-signin\">
<h2 class=\"form-signin-heading\">Update your clan</h2>"
       (apply str (for [id (range 0 (count players))
              :let [p (or (nth players id) "")]]
                    (row-for-id-with-val (+ 1 id) p)))
       (row-for-id-with-val 6 (or extra ""))
       "<div class=\"row\"><div class=\"col-md-10\">
<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Update</button></div></div>
      </form>
"))

(defn render-clan [clan]
  (apply format "<div class=\"list-group\">
  <a href=\"#\" class=\"list-group-item active\">%s</a>
  <a href=\"#\" class=\"list-group-item\">%s</a>
  <a href=\"#\" class=\"list-group-item\">%s</a>
  <a href=\"#\" class=\"list-group-item\">%s</a>
  <a href=\"#\" class=\"list-group-item\">%s</a>
  <a href=\"#\" class=\"list-group-item\">%s</a>
<small>6th player</small>
  <a href=\"#\" class=\"list-group-item\">%s</a>
</div>" (:name clan) (map #(or % "") (conj (:players clan) (:extra clan)))))

(defn other-clans [rest-clans]
  (str "<h2>All Clans:</h2>
<div class=\"well\">
<div class=\"row\">"
       (apply str (map #(format "<div class=\"col-md-6\">%s</div>" (render-clan %))
                       rest-clans))
       "</div></div>"))

(defn available-players [username players]
  (reduce (fn [sum item]
            (str (format "<button type=\"button\" class=\"btn btn-default\" onclick=\"button_press(this)\">%s</button>" item) sum))
          ""
          (difference players #{username})))

(defn view [common-data {:keys [username my-clan rest-clans players-left all-players]}]
  (format "<div class=\"container\">
<h2>Available players:</h1>
<div id=\"players\" class=\"well\">
%s
</div><!-- well -->
<div class=\"row\">
  <div class=\"col-md-4\">%s</div>
  <div class=\"col-md-8\">%s</div>
</div>
</div> </div> </div>
"
          (available-players username players-left)
          (if my-clan (update-my-clan (:players my-clan) (:extra my-clan))
              (form-my-clan username))
          (other-clans rest-clans)))
