/**
 * Created by Flaviu Ratiu on 07/11/2016.
 */

 function getUser() {
    var cookie = Cookies.getJSON('permanent-marker');
    if (cookie && cookie.user) {
        return cookie.user;
    } else {
        return null;
    }
 }

 $(document).ready(function(){
    var user = getUser();
    console.log("user from storage: \n" + user);
    forge.message.broadcastBackground(
        "user",
        user,
        function() {
            console.log("Broadcast user message successful.")
        },
        function() {
            console.log("Failed to broadcast user message.")
        }
    )
 });