/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 $(document).ready(function() {
    $(function() {
        highlightStoredMarks();
    });

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
            createMark(markSpecs);
            highlightTriggered = false;
        }
    });
 });
