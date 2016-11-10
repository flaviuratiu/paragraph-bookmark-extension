/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 $(document).ready(function() {

    var userId;
    forge.message.broadcastBackground(
        "getUser",
        "getUser",
        function(user) {
            console.debug("'getUser' response: " + user);
            if (user) {
                userId = user;
                highlightStoredMarks(userId);
            }
        },
        function(content) {
            console.debug("Failed to publish 'getUser' message. " + content);
        }
    );

    var highlightTriggered = false;
    forge.message.listen(
       "highlight_triggered",
       function() {
            console.debug("'highlight_triggered' message received.");
            highlightTriggered = true;
       },
       function(content) {
            console.debug(content);
       }
    );

    $("body :not(:button, img, script)").mouseup(function(e) {
        if (highlightTriggered) {
            var markSpecs = getMarkSpecs();
            createMark(markSpecs, userId);
            highlightTriggered = false;
        }
    });
 });
