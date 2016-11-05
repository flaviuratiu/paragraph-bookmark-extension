/**
 * Created by Flaviu Ratiu on 10/26/2016.
 */

 function createBookmark() {
     var documentUrl = "loc";
     var textFirstPart = "ab";
     var textLastPart = "b";
     var userId = 1;
     // TODO: extract and parse text parts
     // TODO: extract current url
     // TODO: get user ID from cookie

     $.ajax({
         method: 'POST',
         url: window.apiHost + "/bookmarks/create",
         data: {
             userId: userId,
             documentUrl: documentUrl,
             textFirstPart: textFirstPart,
             textLastPart: textLastPart
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
