/**
 * Created by KGelashvili on 10/26/2015.
 */
var currentPage = 0;
var currentData;
var parcelColumns = ["#","მისამართი", "ბარკოდი", "მოსალოდნელი მიტანის დრო", "მიმღები"];
var userColumns = ["სახელი", "გვარი", "მომხმარებლის სახელი", "პირადი ნომერი", "მობილური"];
var organisationColumns = ["სახელი", 'მობილური', 'მისამართი', 'ელ.ფოსტა'];
var regionColumns = ["სახელი"];
var formatColumns = ["სახელი", "ფასი"];
var filialColumns = ["სახელი", "მისამართი"];
var serviceTypeColumns = ["სახელი", "ფასზე ნამატი"];
var zoneColumns = ["სახელი", "რეგიონი"];
var parcelViewColumns = {
    "address": "მისამართი",
    "barcode": "ბარკოდი",
    "expectedDeliveryDate": "სავარაუდო მიტანის დრო",
    "sentFrom": "გაიგზავნა მისამართიდან",
    "format": "ფორმატი",
    "organisation": "ორგანიზაცია",
    "reciever": "მიმღები",
    //"status": "სტატუსი",
    "serviceType": "სერვისის ტიპი",
    "deliveryDate": "მიტანის დრო",
    "comment": "კომენტარი"
}
var parcelStatuses = {
    "1": "დარეგისტრირდა",
    "2": "აიღო კურიერმა",
    "3": "შემოვიდა საწყობში",
    "4": "გადაეცა კურიერს",
    "5": "მიტანილია"
}
var userTypes = {
    "1": "sa",
    "2": "ადმინისტრატორი",
    "3": "ორგანიზაცია",
    "4": "ორგანიზაციის მომხმარებელი",
    "5": "ბუღალტერი",
    "6": "რეგიონის მენეჟერი",
    "7": "ზონის მენეჟერი",
    "8": "კურიერი",
    "9": "ოპერატორი"
}
var canCreateParcels = false;
var canCreateUsers = false;
var operatorfunctions = false;
var regManagerFunctions = false;
var zoneManagerFunctions = false;

$.getJSON("/getsessionstatus", function (result) {
    if (!result["isactive"]) {
        eraseCookie("projectSessionId");
        window.location.href = "/login.html";
    }
});
$(document).ready(function () {
    console.log($("#loadParcelsButton"))
    $("#loadParcelsButton").click(function () {
        $(".k").attr("class", "k");
        $(this).attr("class", "k nav-active active");

        loadParcelsData(0, "");
    })
    $("#loadUsersButton").click(function () {
        $(".k").attr("class", "k");
        $(this).attr("class", "k nav-active active");

        loadUsersData(0, "");
    })
    $("#logoutBtn").click(function () {
        $.getJSON("/logout", function (result) {
            if (result) {
                eraseCookie("projectSessionId");
                window.location.href = "/login.html";
            }
        })

    });
    if (readCookie("projectUserType") === "1" || readCookie("projectUserType") === "2") {

        $("#navigationUl2").append('<li id="loadOrganisationsButton" class="k">' +
        '<a href="#"><i class="icon-screen-desktop"></i><span data-translate="ორგანიზაციები">ორგანიზაციები</span></a></li>');
        $("#navigationUl2").append('<li id="loadRegionsButton" class="k">' +
        '<a href="#"><i class="icon-picture"></i><span data-translate="რეგიონები">რეგიონები</span></a></li>');
        $("#navigationUl2").append('<li id="loadFormatsButton" class="k">' +
        '<a href="#"><i class="icon-note"></i><span data-translate="ფორმატები">ფორმატები</span></a></li>');
        $("#navigationUl2").append('<li id="loadServiceTypesButton" class="k">' +
        '<a href="#"><i class="icon-note"></i><span data-translate="სერვისის ტიპები">სერვისის ტიპები</span></a></li>');
        $("#navigationUl2").append('<li id="loadZonesButton" class="k">' +
        '<a href="#"><i class="icon-layers"></i><span data-translate="ზონები">ზონები</span></a></li>');
        $("#navigationUl2").append('<li id="loadFilialsButton" class="k">' +
            '<a href="#"><i class="icon-layers"></i><span data-translate="ფილიალები">ფილიალები</span></a></li>');
        $("#loadOrganisationsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ორგანიზაციის დამატება </button>')
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი ორგანიზაციის დამატება")
                var modalBody = $("#modalBody");
                modalBody.html(organizationRegistrationFormTemplate);
                $.getJSON("/getregions", function (result) {
                    if (result) {
                        for (i = 0; i < result.length; i++) {
                            $("#regionIdField").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                        }
                    }
                })
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        email: $("#emailField").val().trim(),
                        name: $("#nameField").val().trim(),
                        address: $("#addressField").val().trim(),
                        mobileNumber: $("#mobileField").val().trim(),
                        regionId: $("#regionIdField").val()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "/createorganisation",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadOrganisationsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }

                    console.log(registerData);
                })
            })
            loadOrganisationsData(0, "");
        });
        $("#loadRegionsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი რეგიონის დამატება </button>')
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი რეგიონის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(regionRegistrationFormTemplate);
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "/createregion",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadRegionsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }


                })
            })
            loadRegionsData(0, "");
        });
        $("#loadFormatsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ფორმატის დამატება </button>')
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი ფორმატის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(formatRegistrationFormTemplate);
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim(),
                        price: $("#priceField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "/createformat",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadFormatsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }


                })
            })
            loadFormatsData();


        });
        $("#loadServiceTypesButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი სერვისის ტიპის დამატება</button>')
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი სერვისის ტიპის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(serviceTypeRegistrationFormTemplate);
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim(),
                        price: $("#priceField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "/createservicetype",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadServiceTypesData()
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }


                })
            })
            loadServiceTypesData();

        });
        $("#loadZonesButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ზონის დამატება</button>')
            $("#addNewButton").click(function () {

                $("#myModalLabel").html("ახალი ზონის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(zoneRegistrationFormTemplate);
                $.getJSON("/getregions", function (result) {
                    if (result) {
                        for (i = 0; i < result.length; i++) {
                            $("#regionIdField").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                        }
                    }
                })
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind();
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        zoneName: $("#nameField").val().trim(),
                        regionId: $("#regionIdField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "/createzone",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadZonesData()
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }
                });
            })
            loadZonesData()
        });
        $("#loadFilialsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            loadFilialsData();
        })

    }

    if (readCookie("projectUserType") === "1" || readCookie("projectUserType") === "2" || readCookie("projectUserType") === "3") {
        canCreateUsers = true;
    }
    if (readCookie("projectUserType") === "3" || readCookie("projectUserType") === "4") {
        canCreateParcels = true;
    }
    if (readCookie("projectUserType") === "6") {
        regManagerFunctions = true;
        loadRegManagerFunctions();
    }
    if (readCookie("projectUserType") === "7") {
        zoneManagerFunctions = true;
        loadZoneManagerFunctions();
    }
    if (readCookie("projectUserType") === "9" ) {
        operatorfunctions = true;
        loadOperatorFunctions();
    }


    loadParcelsData(0, "");
})


function showGiveZoneDialog(k){
    var optionsTemplate=""
    $.getJSON("/getzonesformanager", function (result) {
        for (i = 0; i < result.length; i++) {
            optionsTemplate+="<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>";
        }
        $("#zoneIdField").html(optionsTemplate);


        $("#zoneModal").modal("show")
        $("#saveParcelZone").unbind();
        $("#saveParcelZone").click(function () {
            $.ajax({
                url:'/giveparcelzone',
                data:{
                    parcelIds: k.toString(),
                    zoneId: $("#zoneIdField").val()
                }
            }).done(function (msg) {
                if(msg){
                    if (msg["code"] == 0) {
                        loadParcelsData(currentPage, "")
                        $('#zoneModal').modal("hide");
                        $('#myModal').modal("hide");
                    } else {
                        alert(msg["message"]);
                    }
                }else{

                }
            })
        })
    })

}
