/**
 * Created by kaxa on 5/23/16.
 */

function loadServiceTypesData() {
    $.getJSON("/getservicetypes", function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < serviceTypeColumns.length; i++) {
            var currentElement = serviceTypeColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }
        console.log(result);
        currentData = result;
        for (i = 0; i < currentData.length; i++) {
            var currentElement = currentData[i];

            $("#dataGridBody").append("<tr><td>" + currentElement["name"] + "</td><td>" + currentElement["pricePlus"] + "</td></tr>");

        }

    })
}