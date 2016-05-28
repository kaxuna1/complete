/**
 * Created by kaxa on 5/23/16.
 */
function loadParcelsData(index, search) {
    $.getJSON("/getparcels?index=" + index + "&search=" + search, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < parcelColumns.length; i++) {
            var currentElement = parcelColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }

        console.log(result);
        currentData = result;
        var dataArray = result["content"];
        var totalPages = result["totalPages"];
        var totalElements = result["totalElements"];
        for (i = 0; i < dataArray.length; i++) {
            var currentElement = dataArray[i];

            $("#dataGridBody").append("<tr>" +
                "<td><input value='"+currentElement["id"]+"' type='checkbox' class='parcelCheckBox' /></td>" +
                "<td value='" + i + "' class='gridRow'>" + currentElement["address"] + "</td>" +
                "<td value='" + i + "' class='gridRow'> "+ currentElement["barcode"] + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + (moment(new Date(currentElement["expectedDeliveryDate"])).locale("ka").format("LL")) + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + currentElement["reciever"] + "</td>" +
                "</tr>");

        }
        $('.gridRow').css('cursor', 'pointer');
        $(".gridRow").click(function () {
            console.log(dataArray[$(this).attr("value")])
            $("#myModalLabel2").html("ინფორმაცია გზავნილზე")
            var currentParcel = dataArray[$(this).attr("value")]
            $("#myModalLabel2").html(currentParcel["barcode"])
            $("#parcelDataTable").html("");
            for (key in parcelViewColumns) {
                $("#parcelDataTable").append('<tr class="item-row">' +
                    '<td style="padding-top: 0px;padding-bottom: 0px;">' +
                    '<div class="text-primary">' +
                    '<p><strong>' + parcelViewColumns[key] + '</strong></p>' +
                    '</div>' +
                    '<div id="' + key + '">' + formatParcelData(key, currentParcel[key]) + '</div>' +
                    '</td>' +
                    '</tr>')
                var fieldName = key;
                if(readCookie("projectUserType") === "1"||readCookie("projectUserType") === "2"){
                    if(fieldName==="format"){
                        $('#' + key).editable(function (value, settings) {
                            var data = {id: currentParcel["id"]};
                            console.log(settings);
                            data[settings["name"]] = value;
                            $.ajax({
                                url: "/editparcell",
                                data: data,
                                method: "POST"
                            }).done(function(msg){
                                loadParcelsData(currentPage,"")
                            })


                            return $(this).find('option:selected').text();
                        }, {
                            loadurl : '/getformatsforselect',
                            indicator: 'მიმდინარეობს შენახვა...',
                            tooltip: 'დააკლიკეთ ედიტირებისთვის',
                            name: key+"Id",
                            id: "k",
                            type:"select",
                            submit : 'OK'

                        });
                    }else{
                        if(fieldName==="organisation"){
                            $('#' + key).editable(function (value, settings) {
                                var data = {id: currentParcel["id"]};
                                console.log(settings);
                                data[settings["name"]] = value;
                                $.ajax({
                                    url: "/editparcell",
                                    data: data,
                                    method: "POST"
                                }).done(function(msg){
                                    loadParcelsData(currentPage,"")
                                })


                                return $(this).find('option:selected').text();
                            }, {
                                loadurl : '/getorganisationforselect',
                                indicator: 'მიმდინარეობს შენახვა...',
                                tooltip: 'დააკლიკეთ ედიტირებისთვის',
                                name: key+"Id",
                                id: "k",
                                type:"select",
                                submit : 'OK'

                            });
                        }else{
                            if(fieldName==="serviceType"){
                                $('#' + key).editable(function (value, settings) {
                                    var data = {id: currentParcel["id"]};
                                    console.log(settings);
                                    data[settings["name"]] = value;
                                    $.ajax({
                                        url: "/editparcell",
                                        data: data,
                                        method: "POST"
                                    }).done(function(msg){
                                        loadParcelsData(currentPage,"")
                                    })


                                    return $(this).find('option:selected').text();
                                }, {
                                    loadurl : '/getservicetypesforselect',
                                    indicator: 'მიმდინარეობს შენახვა...',
                                    tooltip: 'დააკლიკეთ ედიტირებისთვის',
                                    name: key+"Id",
                                    id: "k",
                                    type:"select",
                                    submit : 'OK'

                                });
                            }else{
                                $('#' + key).editable(function (value, settings) {
                                        var data = {id: currentParcel["id"]};
                                        console.log(settings);
                                        data[settings["name"]] = value;
                                        $.ajax({
                                            url: "/editparcell",
                                            data: data,
                                            method: "POST"
                                        }).done(function(msg){
                                            loadParcelsData(currentPage,"")
                                        })


                                        return value;
                                    },
                                    {
                                        indicator: 'მიმდინარეობს შენახვა...',
                                        tooltip: 'დააკლიკეთ ედიტირებისთვის',
                                        name: key,
                                        id: "k",
                                        submit : 'OK'

                                    });
                            }
                        }
                    }

                }


            }
            if(readCookie("projectUserType") === "6"){
                $("#giveParcelZone").remove()
                $("#parcelModelFooter").append('<button id="giveParcelZone" type="button" class="btn btn-primary">ზონაზე მინიჭება</button>')
                $("#giveParcelZone").unbind()
                $("#giveParcelZone").click(function () {
                    var parcelIDArray=[currentParcel["id"]];
                    parcelIDArray.push()
                    var popupTemplate =
                        '<div id="zoneModal" class="modal fade">' +
                        '  <div class="modal-dialog">' +
                        '    <div class="modal-content">' +
                        '      <div class="modal-header">' +
                        '        <button type="button" class="close" data-dismiss="modal">&times;</button>' +
                        '        <h4 class="modal-title">ზონის მინიჭება</h4>' +
                        '      </div>' +
                        '      <div class="modal-body">' +
                        '<form>' +
                        '<div class="form-group">' +
                        '<label for="zoneIdField">ზონა</label>: ' +
                        ' <select name="zone" id="zoneIdField"></select></div>' +
                        '</div>' +
                        '</form>' +
                        '      <div class="modal-footer">' +
                        '        <button type="button" class="btn btn-primary" id="saveParcelZone" >შენახვა</button>' +
                        '        <button type="button" class="btn btn-link" data-dismiss="modal">გაუქმება</button>' +
                        '      </div>' +
                        '    </div>' +
                        '  </div>' +
                        '</div>';
                    $("#zoneModal").remove();
                    $("body").append(popupTemplate);
                    showGiveZoneDialog(parcelIDArray);
                })
            }

            $("#parcelHistoryTable").html("")
            for (key in currentParcel["movements"]) {
                $("#parcelHistoryTable").append('<tr class="item-row">' +
                    '<td style="padding-top: 0px;padding-bottom: 0px;">' +
                    '<div class="text-primary">' +
                    '<p><strong>' + currentParcel["movements"][key]["movementText"] + '</strong></p>' +
                    '</div>' +
                    '<p style="margin-bottom: 0px;" class="width-100p">' +
                    '<small>' + moment(new Date(currentParcel["movements"][key]["date"])).locale("ka").format("LLLL") + '</small>' +
                    '</p>' +
                    '</td>' +
                    '</tr>')
            }
            if(readCookie("projectUserType") !== "1"){
                $("#deleteParcelButton").unbind()
                $("#deleteParcelButton").remove()
            }else{
                $("#deleteParcelButton").unbind()
                $("#deleteParcelButton").click(function () {
                    if(confirm("გსურთ ჩანაწერის წაშლა")){
                        $.ajax({
                            url:"/deleteparcel",
                            data:{id:currentParcel["id"]},
                            method:"POST"
                        }).done(function (msg) {
                            if(msg){
                                loadParcelsData(currentPage,"");
                                $('#myModal2').modal("hide");
                            }else{
                                alert("მოხდა შეცდომა")
                            }
                        })
                    }
                })
            }
            $('#myModal2').modal("show");
        })
        for (i = 0; i < totalPages; i++) {
            $("#paginationUl").append('<li value="' + i + '" class="paginate_button ' + (index == i ? 'active"' : '') + '"<a href="#">' + (i + 1) + '</a></li>');
        }
        $(".paginate_button").click(function () {
            //console.log($(this).val())

            currentPage=$(this).val();
            loadParcelsData($(this).val(), "")

        })
        if (canCreateParcels)
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი გზავნილის დამატება </button>')
        else $("#addNewDiv").html('');
        $("#addNewButton").click(function () {
            $("#myModalLabel").html("ახალი გზავნილის დამატება")
            var modalBody = $("#modalBody");
            modalBody.html(parcelRegistrationFormTemplate);
            $.getJSON("/getformats", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#formatIdField").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                    }
                }
            })
            $.getJSON("/getservicetypes", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#serviceTypeIdField").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                    }
                }
            })
            $("#registrationModalSaveButton").unbind()
            $("#registrationModalSaveButton").click(function () {
                var registerData = {
                    reciever: $("#recieverField").val().trim(),
                    address: $("#addressField").val().trim(),
                    sentFrom: $("#sentFromField").val().trim(),
                    formatId: $("#formatIdField").val().trim(),
                    serviceTypeId: $("#serviceTypeIdField").val().trim(),
                    barcode: $("#barcodeField").val().trim()
                }
                var valid = true;
                for (key in registerData) {
                    if (registerData[key] == "") {
                        valid = false
                    }
                }
                if (valid) {
                    $.ajax({
                        url: "/createparcel",
                        method: "POST",
                        data: registerData
                    }).done(function (msg) {
                        if (msg) {
                            if (msg["code"] == 0) {
                                loadParcelsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                alert(msg["message"])
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
            $('#myModal').modal("show");

        })

    })
}

function formatParcelData(key, value) {
    if(!value){
        return "";
    }
    if (key === "expectedDeliveryDate"||key === "deliveryDate")
        return moment(new Date(value)).locale("ka").format("LL");
    if (key === "format")
        return value["name"];
    if (key === "organisation")
        return value["name"]
    if (key === "status")
        return parcelStatuses[value]
    if(key==="serviceType")
        return value["name"]

    return value;

}