/**
 * Created by Flaviu Ratiu on 10/26/2016.
 */

 function createMark() {
     var documentUrl = "loc";
     var text = "ab e";
     var precedingText = "ab";
     var trailingText = "b";
     var emailReminderEnabled = true;
     var userId = 1;
     // TODO: extract and parse text parts
     // TODO: extract current url
     // TODO: get user ID from cookie

     $.ajax({
         method: 'POST',
         url: window.apiHost + "/marks/create",
         data: {
             userId: userId,
             documentUrl: documentUrl,
             text: text,
             precedingText: precedingText,
             trailingText: trailingText,
             emailReminderEnabled: emailReminderEnabled
         },
         dataType: "json",
         crossDomain: true
     })
     .done(function() {
         console.log("Highlighting saved.");
         // TODO: Display bookmark
     })
     .fail(function() {
         console.log("Failed to save highlighting.");
         // TODO: Bookmark creation failure message
     })
 }
