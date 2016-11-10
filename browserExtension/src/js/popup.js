/**
 * Created by Flaviu Ratiu on 04/11/2016.
 */

 function broadcastHighlightMessage() {
    forge.message.toFocussed(
        "highlight_triggered",
        "Capture next selection.",
        function(){
            console.debug("Highlight done successfully.");
        },
        function(){
            console.debug("Failed to trigger highlighting.");
            showMessage("error", "Failed to trigger highlighting. Please try again later.");
    });
    if ($(".message").is(":hidden")) {
        showMessage("success", "You can now select the text for highlighting.");
        successMessage.show();
    }
  }

  function showMessage(className, text) {
      var errorMessage = $(".message");
      $(errorMessage).attr("class", "message");
      $(errorMessage).addClass(className);
      $(errorMessage).empty();
      $(errorMessage)
          .append($("<p>")
              .text(text)
          );
      errorMessage.show();
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
                showMessage("error", "Please log in before highlighting text.");
             }
         }
     );
 });