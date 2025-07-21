const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
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

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        const dt = new Date(data);
                        return dt.toLocaleString();
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "render": function (data, type, row) {
                        return `<a onclick="updateRow(${row.id})"><span class="fa fa-pencil"></span></a>`;
                    },
                    "orderable": false
                },
                {
                    "render": function (data, type, row) {
                        return `<a onclick="deleteRow(${row.id})"><span class="fa fa-remove"></span></a>`;
                    },
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data) {
                if (data.excess) {
                    $(row).addClass("table-danger");
                } else {
                    $(row).addClass("table-success");
                }
            }
        })
    );
});