/**
 * Created by Flaviu Ratiu on 07/11/2016.
 */

 function getUser() {
    var cookie = Cookies.getJSON("permanent-marker-app");
    if (cookie && cookie.user) {
        return cookie.user;
    } else {
        return null;
    }
 }

 (function(){
    var user = getUser();
    console.debug("user from storage: " + user);
    forge.message.broadcastBackground(
        "userAuthenticated",
        user,
        function() {
            console.debug("Broadcast user message successful.")
        },
        function() {
            console.debug("Failed to broadcast user message.")
        }
    )
 })();