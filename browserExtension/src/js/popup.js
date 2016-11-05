/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 function broadcastHighlightMessage() {
    forge.message.toFocussed(
        "highlight_triggered",
        "Capture next selection.",
        function(){console.log("Highlight done successfully.")},
        function(){console.log("Failed to trigger highlighting.")
    })
  }

 $(document).ready(function() {
     $("#add-bookmark").click(
         function(e) {
             e.preventDefault();
             broadcastHighlightMessage();
         }
     );
 });