/**
 * Created by Flaviu Ratiu on 07/11/2016.
 */

 (function() {
    forge.message.listen(
       "user",
       function(user) {
            broadcastUser(user);
       },
       function(content) {
            console.log(content);
       }
    );
 })();

 function broadcastUser(user) {
    forge.message.broadcast(
        "currentUser",
        user,
        function() {
            console.log("Background broadcast successful.")
        },
        function() {
            console.log("Background broadcast failed.")
        }
    );
 }