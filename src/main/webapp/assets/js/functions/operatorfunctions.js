/**
 * Created by kaxa on 5/23/16.
 */
var currentShemosvlaParcels = {};
var currentGasvlaParcels = {};
var currentGasvlaDataFromServer={};
function loadOperatorFunctions() {
    $("#opBtns").append('<li><Button id="shemosvlaBtn" class="btn-default">საწყობში მიღება</Button></li>');
    $("#opBtns").append('<li><Button id="gasvlaBtn" class="btn-default">საწყობიდან გატანა</Button></li>');

    $("#shemosvlaBtn").click(function () {
        showModalWithTableInside(function (header, body) {
            header.html("საწყობში მიღება")
            console.log(body);
            body.append("<div><input id='shemosvlaBarcodeText' type='text'/><button id='addToShemosvlaBtn' class='btn'>დამატება</button></div>" +
                "<div><table class='table'>" +
                "<tbody id='shemosvlaTableBody'>" +
                "</tbody>" +
                "</table></div>");
            for (var key in currentShemosvlaParcels) {
                if (currentShemosvlaParcels[key]) {
                    $("#shemosvlaTableBody").append("<tr style='width: 100%;' id='" + currentShemosvlaParcels[key] + "tr'>" +
                        "<td style='width: 50%;'>" + currentShemosvlaParcels[key] + "</td>" +
                        "<td><button value='" + currentShemosvlaParcels[key] + "' class='btn btnRemove'>X</button></td></tr>");
                    $(".btnRemove").unbind("click");
                    $(".btnRemove").click(function () {
                        var removeVal = $(this).attr("value");
                        currentShemosvlaParcels[removeVal] = undefined;
                        $("#" + removeVal + "tr").remove();
                    })
                }
            }
            $("#addToShemosvlaBtn").click(function () {
                var shemosvlaBarcodeText = $("#shemosvlaBarcodeText");
                if (shemosvlaBarcodeText.val() && !currentShemosvlaParcels[shemosvlaBarcodeText.val()]) {
                    var currentShemosvlaBarrcode = shemosvlaBarcodeText.val();
                    currentShemosvlaParcels[currentShemosvlaBarrcode] = currentShemosvlaBarrcode;
                    $("#shemosvlaTableBody").append("<tr style='width: 100%;' id='" + currentShemosvlaBarrcode + "tr'>" +
                        "<td style='width: 50%;'>" + currentShemosvlaBarrcode + "</td>" +
                        "<td><button value='" + currentShemosvlaBarrcode + "' class='btn btnRemove'>X</button></td></tr>");
                    $(".btnRemove").unbind("click");
                    $(".btnRemove").click(function () {
                        var removeVal = $(this).attr("value");
                        currentShemosvlaParcels[removeVal] = undefined;
                        $("#" + removeVal + "tr").remove();
                    })
                }
                console.log(currentShemosvlaParcels);
            })

        }, {
            "დადასტურება": function () {
                var sendData = [];
                for (key in currentShemosvlaParcels) {
                    if (currentShemosvlaParcels[key]) {
                        sendData.push(currentShemosvlaParcels[key])
                    }
                }
                $.ajax({
                    url: "shemosvla",
                    data: {parcels: JSON.stringify(sendData)},
                    type: "POST"
                }).done(function (result) {
                    if (result) {
                        alert("ამანათები მიღებულია სწყობში")
                    }
                })
            }
        });
    });
    $("#gasvlaBtn").click(function () {
        currentGasvlaParcels = {};
        showModalWithTableInside(function (header, body) {
            header.html("საწყობიდან გატანა")
            console.log(body);
            body.append("<div><select id='giveCourierSelect'><option value='0'>აირჩიეთ კურიერი</option></select></div>" +
                "<div>" +
                "<input id='gasvlaBarcodeText' type='text'/><button id='addToGasvlaBtn' class='btn'>დამატება</button>" +
                "</div>" +
                "<div><table class='table'>" +
                "<tbody id='gasvlaTableBody'>" +
                "</tbody>" +
                "</table></div>");
            $.getJSON("/getcuriersforoperator", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#giveCourierSelect").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + " " + result[i]["surname"] + "</option>")
                    }
                }
                $("#giveCourierSelect").change(function () {
                    currentGasvlaDataFromServer={};
                    var currentId = $(this).val();
                    if (currentId) {
                        $.getJSON("/getcourierparcelsforsend?id=" + currentId, function (result2) {
                            for (key in result2) {
                                currentGasvlaDataFromServer[result2[key]["barcode"]]=result2[key]["barcode"];
                                $("#gasvlaTableBody").append("<tr id='" + result2[key]["barcode"] + "tr'><td>" + result2[key]["barcode"] + "</td>" +
                                    "<td>" + result2[key]["address"] + "</td>" +
                                    "</tr>")
                            }
                        })
                    } else {
                        $("#gasvlaTableBody").html("");
                    }

                })

            });
            $("#addToGasvlaBtn").click(function () {
                var currentBarcode = $("#gasvlaBarcodeText").val();
                if(currentBarcode){
                    if(currentGasvlaDataFromServer[currentBarcode]){
                        currentGasvlaParcels[currentBarcode]=currentBarcode;
                        $("#"+currentBarcode+"tr").css("background","greenyellow");
                        $("#gasvlaBarcodeText").val("")
                    }else{
                        alert("ეს ამანათი არ ეკუთვნის მითითებულ კურიერს");
                    }
                }

            })
        },{
            "დადასტურება": function () {
                var sendData = [];
                for (key in currentGasvlaParcels) {
                    if (currentGasvlaParcels[key]) {
                        sendData.push(currentGasvlaParcels[key])
                    }
                }
                $.ajax({
                    url: "gasvla",
                    data: {parcels: JSON.stringify(sendData)},
                    type: "POST"
                }).done(function (result) {
                    if (result) {
                        alert("ამანათები გადაცემულია კურიერზე")
                    }
                })
            }
        });
    });
}
