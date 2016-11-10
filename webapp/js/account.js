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
                        .append($("<a>")
                            .attr("href", items[index].documentUrl)
                            .attr("target", "_blank")
                            .text(items[index].documentUrl)
                        )
                    )
                    .append($("<td>")
                        .text(items[index].text)
                    )
                    .append($("<td>")
                        .append($("<input>")
                            .attr("type", "checkbox")
                            .attr("class", "select-mark")
                            .data("markId", items[index].id)
                        )
                    )
                )
            itemNumber += 1;
        });
        $("tbody tr:odd").addClass('alternate');
        $("#marks").show();
        $("#delete-marks").show();
    }
 }

 function displayEmptyTableBody() {
    $("#marks").hide();
    $("#delete-marks").hide();
    $(".message").addClass("info");
    $(".message")
        .append($("<p>")
            .text("You haven't highlighted any items yet.")
        )
        .append($("<p>")
            .text("In order to do so press the Highlight Text button from the Permanent Marker extension")
        )
        .append($("<p>")
            .text("and then select the text which you want to highlight.")
        )
    $(".message").show();
 }

 $(document).ready(function() {
    var cookie = Cookies.getJSON(window.appCookieName);
    if (cookie && cookie.user) {
        console.debug("Valid user in cookie.");
        var userId = cookie.user;
        var pageNumber = 1;
        var pageSize = 100;
        getUsersHighlightedItems(userId, pageNumber, pageSize);

        $("#delete-marks").click(function() {
            console.debug("Delete event captured.");
            deleteSelectedItems(cookie.user);
        });

        $("#logout").click(function () {
            Cookies.remove(window.appCookieName);
        })
    } else {
        console.debug("Invalid user in cookie.");
        displayEmptyTableBody();
    }
 });

 function deleteSelectedItems(userId) {
    console.debug("userId in delete call: " + userId);
    var selectedItems = [];
    $(".select-mark:checkbox:checked").each(function(index) {
        var markId = $(this).data("markId");
        selectedItems.push(markId);
    });

    if (selectedItems.length > 0) {
        console.debug("Selected items: " + selectedItems);
        $.ajax({
            method: "POST",
            url: window.apiHost + "/marks/delete",
            crossDomain: true,
            contentType: "application/json",
            data: JSON.stringify({
                userId: userId,
                markIds: selectedItems
            })
        })
        .done(function() {
            console.debug("Batch delete successful.");
            $("#marks").find("tbody").empty();
            var pageNumber = 1;
            var pageSize = 100;
            getUsersHighlightedItems(userId, pageNumber, pageSize);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.debug("Batch delete failed.\n" + jqXHR + " " + textStatus + "\n" + errorThrown);
        });
    }
 }

