/**
 * Created by Flaviu Ratiu on 08/11/2016.
 */

 function getUsersHighlightedItems(userId, pageNumber, pageSize) {
    $.ajax({
        method: "GET",
        url: window.apiHost + "/marks/get?userId=" + userId + "&page=" + pageNumber + "&size=" + pageSize,
        crossDomain: true
    })
    .done(function(content) {
        console.debug("Retrieved items:\n" + content.marks);
        displayHighlightedItems(content.marks);
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        console.debug("Failed to retrieve highlighted items.\n" + jqXHR + " " + textStatus + " " + errorThrown);
    })
 }

 function displayHighlightedItems(items) {
    var itemNumber = 1;
    if (items.length == 0) {
        console.debug("No items.");
        displayEmptyTableBody();
    } else {
        console.debug("Highlighted items found.");
        $(items).each(function(index) {
            $("#marks").find("tbody")
                .append($("<tr>")
                    .append($("<td>")
                        .text(itemNumber)
                    )
                    .append($("<td>")
                        .text(items[index].documentUrl)
                    )
                    .append($("<td>")
                        .text(items[index].text)
                    )
                    .append($("<td>")
                        .append($("<input>")
                            .attr("type", "checkbox")
                            .attr("class", "select-mark")
                        )
                    )
                )
            itemNumber += 1;
        });
    }
 }

 function displayEmptyTableBody() {
    $("#marks").find("tbody")
        .append($("<tr>")
            .attr("class", "empty-table-body")
            .text("No highlighted items available.")
        );
 }

 $(document).ready(function() {
    var cookie = Cookies.getJSON(window.appCookieName);
    if (cookie && cookie.user) {
        console.debug("Valid user in cookie.");
        var userId = cookie.user;
        var pageNumber = 1;
        var pageSize = 20;
        getUsersHighlightedItems(userId, pageNumber, pageSize);
    } else {
        console.debug("Invalid user in cookie.");
        displayEmptyTableBody();
    }
 });