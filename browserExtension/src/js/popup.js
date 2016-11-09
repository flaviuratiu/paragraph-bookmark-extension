/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 function broadcastHighlightMessage() {
    forge.message.toFocussed(
        "highlight_triggered",
        "Capture next selection.",
        function(){console.debug("Highlight done successfully.")},
        function(){console.debug("Failed to trigger highlighting.")
    })
  }

 $(document).ready(function() {
     var cookie = Cookies.get("permanent-marker-extension");
     if (cookie && cookie.user) {
        $("#log-in").hide();
        $("#manage-account").show();
     }

     $("#add-mark").click(
         function(e) {
             e.preventDefault();
             if (cookie && cookie.user) {
                broadcastHighlightMessage();
             } else {
                $("#error-message").text("Please log in to the Permanent Marker app.");
                $("#error-message").show();
             }
         }
     );
 });