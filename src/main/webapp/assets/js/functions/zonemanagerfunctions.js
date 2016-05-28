/**
 * Created by kaxa on 5/24/16.
 */
/**
 * Created by kaxa on 5/24/16.
 */
var parcelsToGiveCourier=[];
function loadZoneManagerFunctions(){
    $("#opBtns").append('<li><Button id="giveCourierBtn" class="btn-default">კურიერის მინიჭება</Button></li>');
    $("#giveCourierBtn").click(function () {
        $(".parcelCheckBox:checked").each(function () {
            parcelsToGiveCourier.push($(this).attr("value"));
        })
        showModalWithTableInside(function (header, body) {
            header.html("კურიერის მინიჭება");
            body.html("<select id='giveZoneSelect'><option value='0'>აირჩიეთ კურიერი</option></select>")
            $.getJSON("/getcuriersformanager", function (result) {
                if (result) {
                    for (i = 0; i < result.length; i++) {
                        $("#giveZoneSelect").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"]+" "+ result[i]["surname"] + "</option>")
                    }
                }
            });
        },{
            "დადასტურება":function () {
                $.ajax({
                    url:"givecourier",
                    data:{parcels:JSON.stringify(parcelsToGiveCourier),courier:$("#giveZoneSelect").val()},
                    type:"POST"
                }).done(function (result) {
                    if(result){
                        alert("ამანათები მინიჭებულია კურიერზე")
                        loadParcelsData(0,"")
                    }
                })
            }
        })
    })

}