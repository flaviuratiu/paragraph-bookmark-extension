/**
 * Created by Flaviu Ratiu on 07/11/2016.
 */

 (function() {
    forge.message.listen(
       "userNotAuthenticated",
       function(user) {
            Cookies.remove("permanent-marker-extension");
       },
       function(content) {
            console.debug(content);
       }
    );
 })();

 (function() {
    forge.message.listen(
       "userAuthenticated",
       function(user) {
            Cookies.set(
                "permanent-marker-extension", {
                user: user
            });
       },
       function(content) {
            console.debug(content);
       }
    );
 })();

 (function() {
    forge.message.listen(
        "getUser",
        function(content, reply) {
            var cookie = Cookies.getJSON("permanent-marker-extension");
            if (cookie && cookie.user) {
                reply(cookie.user);
            }
        },
        function(content) {
            console.debug(content);
        }
    );
 })();