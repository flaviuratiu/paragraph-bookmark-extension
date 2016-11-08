/**
 * Created by Flaviu Ratiu on 07/11/2016.
 */

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
            reply(cookie.user);
        },
        function(content) {
            console.debug(content);
        }
    );
 })();