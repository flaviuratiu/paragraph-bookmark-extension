/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 function broadcastHighlightMessage() {
    var errorMessage = $(".error-message");
    var successMessage = $(".success-message");
    forge.message.toFocussed(
        "highlight_triggered",
        "Capture next selection.",
        function(){
            console.debug("Highlight done successfully.");
        },
        function(){
            console.debug("Failed to trigger highlighting.");
            errorMessage.text("Failed to trigger highlighting. Please try again later.");
            errorMessage.show();
    });
    if ($(errorMessage).is(":hidden")) {
        successMessage.text("You can now select the text for highlighting.");
        successMessage.show();
    }
  }

 $(document).ready(function() {
     var cookie = Cookies.getJSON("permanent-marker-extension");
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
                $(".error-message").text("Please log in before highlighting text.");
                $(".error-message").show();
             }
         }
     );
 });