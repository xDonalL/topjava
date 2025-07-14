const mealAjaxUrl = "rest/profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

function openModal() {
    $("#mealForm")[0].reset();
    $("#dateTime").val(new Date().toISOString().slice(0, 16));
    $("#editModal").modal("show");
}

function saveMeal() {
    const meal = {
        dateTime: $("#dateTime").val(),
        description: $("#description").val(),
        calories: $("#calories").val()
    };

    $.ajax({
        type: "POST",
        url: "/rest/profile/meals",
        contentType: "application/json",
        data: JSON.stringify(meal),

        success: function () {
            $("#editModal").modal("hide");
            ctx.updateTable();
        },
        error: function (xhr) {
            alert("Ошибка: " + xhr.responseText);
        }
    });
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});