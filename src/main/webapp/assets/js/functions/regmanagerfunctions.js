/**
 * Created by kaxa on 5/24/16.
 */
var parcelsToGiveZone=[];
var parcelsToSendToFilial=[];
function loadRegManagerFunctions(){
    $("#opBtns").append('<li><Button id="giveZoneBtn" class="btn-default">ზონის მინიჭება</Button></li>');
    $("#opBtns").append('<li><Button id="sendToFilialBtn" class="btn-default">სხვა ფილიალში გადამისამართება</Button></li>');
    $("#giveZoneBtn").click(function () {
        $(".parcelCheckBox:checked").each(function () {
            parcelsToGiveZone.push($(this).attr("value"));
        });
        showModalWithTableInside(function (header, body) {
            header.html("ზონის მინიჭება");
            body.html("<select id='giveZoneSelect'></select>")
            $.getJSON("/getzonesformanager", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#giveZoneSelect").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                    }
                }
            });
        },{
            "დადასტურება":function () {
                $.ajax({
                    url:"giveparcelzone",
                    data:{parcels:JSON.stringify(parcelsToGiveZone),zoneId:$("#giveZoneSelect").val()},
                    type:"POST"
                }).done(function (result) {
                    if(result){
                        alert("ამანათები მინიჭებულია ზონაზე")
                        loadParcelsData(0,"")
                    }
                })
            }
        })
    });
    $("#sendToFilialBtn").click(function () {
        parcelsToSendToFilial=[];
        $(".parcelCheckBox:checked").each(function () {
            parcelsToSendToFilial.push($(this).attr("value"));
        })
        showModalWithTableInside(function (header, body) {
            header.html("ზონის მინიჭება");
            body.html("<select id='regionSelect'><option value='0'>აირჩიეთ რეგიონი</option></select><br/>" +
                "<select id='filials'><option value='0'>აირჩიეთ რეგიონი</option></select>")
            $.getJSON("/getregions", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#regionSelect").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                    }
                    $("#regionSelect").change(function () {
                        var regionVal=$(this).val();
                        if(regionVal){
                            $.getJSON("getregionfilials?id="+regionVal,function (result2) {
                                $("#filials").html("");
                                for(key2 in result2){
                                    $("#filials").append("<option value='" + result2[key2]["id"] + "'>" + result2[key2]["name"] + "</option>")
                                }
                            })
                        }

                    })
                }
            });
        },{
            "დადასტურება":function () {
                $.ajax({
                    url:"sendtofilial",
                    data:{parcels:JSON.stringify(parcelsToSendToFilial),filialId:$("#filials").val()},
                    type:"POST"
                }).done(function (result) {
                    if(result){
                        alert("ამანათები გადაგზავნილია ფილიალში")
                        loadParcelsData(0,"")
                    }
                })
            }
        })
    });


}