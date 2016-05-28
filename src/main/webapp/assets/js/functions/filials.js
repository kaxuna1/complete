/**
 * Created by kaxa on 5/24/16.
 */
function loadFilialsData() {
    $.getJSON("/getfilials", function (result) {
        $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ფილიალის დამატება</button>')
        $("#addNewButton").click(function () {
            $("#myModalLabel").html("ახალი ფილიალის დამატება")
            var modalBody = $("#modalBody");
            modalBody.html(filialRegistrationFormTemplate);
            $.getJSON("/getregions", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#regionIdField").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                    }
                }
            });
            $('#myModal').modal("show");
            $("#registrationModalSaveButton").unbind()
            $("#registrationModalSaveButton").click(function(){
                var registerData = {
                    name: $("#nameField").val().trim(),
                    address: $("#addressField").val().trim(),
                    regionId: $("#regionIdField").val().trim()
                };
                var valid = true;
                for (key in registerData) {
                    if (registerData[key] == "") {
                        valid = false
                    }
                }
                if (valid) {
                    $.ajax({
                        url: "/createfilial",
                        method: "POST",
                        data: registerData
                    }).done(function (msg) {
                        if (msg) {
                            if (msg["code"] == 0) {
                                loadFilialsData()
                                $('#myModal').modal("hide");
                            } else {
                                alert(msg["message"]);
                            }

                        } else {
                            $('#myModal').modal("hide");
                            alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                        }
                    })
                } else {
                    alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                }
            })
        });
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < filialColumns.length; i++) {
            var currentElement = filialColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }
        console.log(result);
        currentData = result;
        for (i = 0; i < currentData.length; i++) {
            var currentElement = currentData[i];

            $("#dataGridBody").append("<tr><td>" + currentElement["name"] + "</td><td>" + currentElement["address"] + "</td></tr>");

        }

    })
}