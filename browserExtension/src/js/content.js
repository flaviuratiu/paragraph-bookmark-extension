/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 $(document).ready(function() {
    forge.message.listen(
       "highlight_triggered",
       function() {
        console.log("'highlight_triggered' message received.")
        createBookmark();
       },
       function(content) {
        console.log(content)
       }
    );
 });
