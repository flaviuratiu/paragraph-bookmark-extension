/**
 * Created by Flaviu Ratiu on 10/26/2016.
 */

 function createMark(markSpecs) {
     var documentUrl = "loc";
     var emailReminderEnabled = true;
     var userId = 1;
     // TODO: extract current url
     // TODO: get user ID from cookie

     $.ajax({
         method: 'POST',
         url: window.apiHost + "/marks/create",
         data: {
             userId: userId,
             documentUrl: documentUrl,
             text: markSpecs.selected,
             precedingText: markSpecs.before,
             trailingText: markSpecs.after,
             emailReminderEnabled: emailReminderEnabled
         },
         dataType: "json",
         crossDomain: true
     })
     .done(function() {
         console.log("Highlighting saved.");
         highlightText(markSpecs.before, markSpecs.selected, markSpecs.after);
     })
     .fail(function() {
         console.log("Failed to save highlighting.");
         // TODO: Bookmark creation failure message
     })
 }

 function highlightText(precedingText, text, trailingText) {
    var regex = new RegExp("(" + precedingText + ")(" + text + ")(" + trailingText + ")","gim");
    $("body").markRegExp(regex, {
        "acrossElements": true,
        "ignoreGroups": 1,
        "caseSensitive": true,
        "iframes": true
    });
 }

 function getMarkSpecs() {
    var sel, range, tempRange, containerEl = null, before = "", after = "";
    if (typeof window.getSelection != "undefined") {
        sel = window.getSelection();
        if (sel.rangeCount) {
            range = sel.getRangeAt(0);
            containerEl = range.commonAncestorContainer;
            if (containerEl.nodeType != 1) {
                containerEl = containerEl.parentNode;
            }
        } else {
            range = document.createRange();
            range.collapse(true);
        }
        tempRange = document.createRange();
        tempRange.selectNodeContents(containerEl);
        tempRange.setEnd(range.startContainer, range.startOffset);
        before = tempRange.toString();

        tempRange.selectNodeContents(containerEl);
        tempRange.setStart(range.endContainer, range.endOffset);
        after = tempRange.toString();

        tempRange.detach();
    } else if ( (sel = document.selection) && sel.type != "Control") {
        range = sel.createRange();
        containerEl = range.parentElement();
        tempRange = document.body.createTextRange();
        tempRange.moveToElementText(containerEl);
        tempRange.setEndPoint("EndToStart", range);
        before = tempRange.text;

        tempRange.moveToElementText(containerEl);
        tempRange.setEndPoint("StartToEnd", range);
        after = tempRange.text;
    }
    return {
        before: before,
        selected: sel.toString(),
        after: after
    };
 }
