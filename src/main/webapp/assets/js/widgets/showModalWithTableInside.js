/**
 * Created by kakha on 1/14/2016.
 */
function showModalWithTableInside(callback,callbacks) {
    var random=Math.floor((Math.random() * 10000) + 1);

    var popupTemplate =
        '<div id="promptModal'+random+ '" class="modal fade">' +
        '  <div class="modal-dialog">' +
        '    <div class="modal-content">' +
        '      <div class="modal-header">' +
        '        <button type="button" class="close" data-dismiss="modal">&times;</button>' +
        '        <h4 id="modalWithTableHeader'+random+'" class="modal-title"></h4>' +
        '      </div>' +
        '      <div id="modalWithTableBody'+random+'" class="modal-body">' +
        '</div>' +
        '      <div id="modalFooterDynamic'+random+'" class="modal-footer">' +
        '        <button type="button" class="btn btn-link" data-dismiss="modal">გაუქმება</button>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>';

    var thisModal = $(popupTemplate).modal("show").on('hidden.bs.modal', function () {
        $("#promptModal"+random).remove();
    }).on('shown.bs.modal', function () {

        callback($(this).find("#modalWithTableHeader"+random),$(this).find("#modalWithTableBody"+random));

        for (var key in callbacks) {
            $(this).find("#modalFooterDynamic"+random).append('<button id="' + key + 'Btn" type="button" class="btn btn-link">' + key + '</button>')
            $("#" + key + "Btn").click(function () {
                callbacks[key]()
                thisModal.modal("hide");

            });
        }

    });
}