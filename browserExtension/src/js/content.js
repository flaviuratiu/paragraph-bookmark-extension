/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 $(document).ready(function() {
    var userId;
    forge.message.listen(
       "currentUser",
       function(content) {
            console.log("'currentUser' message received.");
            userId = content;
            if (userId) {
                highlightStoredMarks(userId);
            }
       },
       function(content) {
            console.log(content);
       }
    );

    var highlightTriggered = false;
    forge.message.listen(
       "highlight_triggered",
       function() {
            console.log("'highlight_triggered' message received.");
            highlightTriggered = true;
       },
       function(content) {
            console.log(content);
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
