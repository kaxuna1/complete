/**
 * Created by kaxa on 5/23/16.
 */

function loadFormatsData() {
    $.getJSON("/getformats", function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < formatColumns.length; i++) {
            var currentElement = formatColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }
        console.log(result);
        currentData = result;
        for (i = 0; i < currentData.length; i++) {
            var currentElement = currentData[i];

            $("#dataGridBody").append("<tr><td>" + currentElement["name"] + "</td><td>" + currentElement["price"] + "</td></tr>");

        }

    })
}