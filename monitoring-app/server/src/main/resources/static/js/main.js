//---------------------------------------------------------------
// Init PushClient via WebSocket
var pushClient = new PushClient();

//---------------------------------------------------------------
// Equipment: Model + Chart
var chart = null;
var model = new EquipmentModel(200);
var equipmentTable = $("#equipment tbody");

model.onAdded(function (equipment) {
    var names = model.getEquipmentNames();
    var data = {
        element: 'rt_chart',
        data: model.getRecords(),
        xkey: 'timing',
        ykeys: names,
        labels: names,
        pointSize: 0,
        ymin: 15,
        ymax: 90,
        xLabels: '5sec',
        goals: [70, 75],
        goalLineColors: ['#FFFF00', '#ff0000'],
        goalStrokeWidth: 4,
        eventStrokeWidth: 1,
        hideHover: 'auto'
    };

    $("#rt_chart").html('');
    chart = new Morris.Line(data);

    equipmentTable.append($(
        "<tr class='" + equipment.name + (equipment.online ? "" : " table-danger") + "'>" +
        "<td class='name'>" + equipment.name + "</td>" +
        "<td class='online'>" + equipment.online + "</td>" +
        "<td class='temperature'>" + equipment.temperature + "</td>" +
        "<td class='humidity'>" + equipment.humidity + "</td>" +
        "<td class='action'><button class='create'>Create</button><button class='shutdown'>Shutdown</button></td>" +
        "</tr>"));
});

model.onUpdated(function (equipment) {
    var tr = equipmentTable.find("tr." + equipment.name);
    tr.removeClass();
    tr.addClass(equipment.name);

    if (equipment.temperature >= 70) {
        tr.addClass("table-warning");
    }

    if (!equipment.online) {
        tr.addClass("table-danger");
    }

    tr.children().each(function (index, td) {
        if (td.className != "action") {
            td.innerText = equipment[td.className];
        }
    });
});

model.onTick(function () {
    chart.setData(model.getRecords());
});

equipmentTable.on("click", ".action button", function (event) {
    var action = event.target.className;
    var data = {
        name: $(event.target).parents("tr").first().children("td.name").html(),
        condition: "result.add('Simple alert');",
        active: false
    };

    if (action == "create") {
        pushClient.saveAlert(data);
    } else if (action == "shutdown") {
        $.get('/equipments/' + data.name + '/shutdown');
    }
});


//---------------------------------------------------------------
// TemperatureSnapshot: Model + Chart
var tempChart = null;
var tempModel = new TemperatureModel(288);

tempModel.onAdded(function (records) {
    var names = tempModel.getEquipmentNames();
    var data = {
        element: 'delay_chart',
        data: records,
        xkey: 'timing',
        ykeys: names,
        labels: names,
        pointSize: 0,
        ymin: 15,
        ymax: 90,
        xLabels: '5sec',
        goals: [70, 75],
        goalLineColors: ['#FFFF00', '#ff0000'],
        goalStrokeWidth: 4,
        eventStrokeWidth: 1,
        hideHover: 'auto'
    };

    $("#delay_chart").html('');
    tempChart = new Morris.Line(data);
});

tempModel.onUpdated(function (records) {
    tempChart.setData(records);
});

//---------------------------------------------------------------
// Rele: Model
var reles = {};
var releTable = $("#rele tbody");
releTable.on("click", ".action button", function (event) {
    var action = event.target.className;
    var data = {
        name: $(event.target).parents("tr").first().children("td.name").html(),
        condition: "result = false; force = false;",
        manual: false,
        active: false
    };

    if (action == "create") {
        pushClient.saveCondition(data);
    }
});

//---------------------------------------------------------------
// Condition: Model
var conditionTable = $("#condition tbody");
conditionTable.on("click", ".action button", function (event) {
    var action = event.target.className;
    var tr = $(event.target).parents("tr").first();
    var condition = {};


    if (action == "update") {
        condition.name = tr.find("td.name").html();
        condition.condition = tr.find("td.condition textarea").val();
        condition.manual = tr.find("td.manual input").is(":checked");
        condition.active = tr.find("td.active input").is(":checked");

        console.log(condition);
        pushClient.saveCondition(condition);
    }
});

//---------------------------------------------------------------
// Alert: Model
var alertTable = $("#alert tbody");
alertTable.on("click", ".action button", function (event) {
    var action = event.target.className;
    var tr = $(event.target).parents("tr").first();
    var alert = {};


    if (action == "update") {
        alert.name = tr.find("td.name").html();
        alert.condition = tr.find("td.condition textarea").val();
        alert.active = tr.find("td.active input").is(":checked");

        console.log(alert);
        pushClient.saveAlert(alert);
    }
});

//---------------------------------------------------------------
// Init PushClient via WebSocket
$.get('/equipments', function (result) {
    console.log(result);
    result.forEach(function (equipment) {
        model.update(equipment);
    });

    pushClient.subscribeEquipment(function (equipment) {
        console.log(equipment);
        model.update(equipment);
    });
});

$.get('/temperatures', function (data) {
    tempModel.update(data);

    pushClient.subscribeTemperature(function (temperature) {
        console.log(temperature);
        tempModel.update([temperature]);
    });
});

$.get('/reles', function (data) {
    var insertCallback = function (rele) {
        releTable.append($(
            "<tr class='" + rele.name + "'>" +
            "<td class='name'>" + rele.name + "</td>" +
            "<td class='power'>" + rele.power + "</td>" +
            "<td class='action'><button class='create'>Create</button></td>" +
            "</tr>"));
    };

    var updateCallback = function (rele, tr) {
        tr.removeClass();
        tr.addClass(rele.name);

        if (rele.power) {
            tr.addClass("table-success");
        }

        tr.children().each(function (index, td) {
            if (td.className != "action") {
                td.innerText = rele[td.className];
            }
        });
    };

    data.forEach(insertCallback);

    pushClient.subscribeRele(function (rele) {
        console.log(rele);
        reles[rele.name] = rele;

        var tr = releTable.find("tr." + rele.name);
        if (tr.length == 0) {
            insertCallback(rele)
        } else {
            updateCallback(rele, tr);
        }
    });
});

$.get('/conditions', function (data) {
    var insertCallback = function (condition) {
        conditionTable.append($(
            "<tr class='" + condition.name + "'>" +
            "<td class='name'>" + condition.name + "</td>" +
            "<td class='condition'><textarea>" + condition.condition + "</textarea></td>" +
            "<td class='manual'><input type='checkbox' " + (condition.manual ? "checked" : "") + "></td>" +
            "<td class='active'><input type='checkbox' " + (condition.active ? "checked" : "") + "></td>" +
            "<td class='action'><button class='update'>Update</button></td>" +
            "</tr>"));
    };

    var updateCallback = function (condition, tr) {
        tr.empty();
        tr.append($(
            "<td class='name'>" + condition.name + "</td>" +
            "<td class='condition'><textarea>" + condition.condition + "</textarea></td>" +
            "<td class='manual'><input type='checkbox' " + (condition.manual ? "checked" : "") + "></td>" +
            "<td class='active'><input type='checkbox' " + (condition.active ? "checked" : "") + "></td>" +
            "<td class='action'><button class='update'>Update</button></td>")
        );
    };

    data.forEach(insertCallback);

    pushClient.subscribeCondition(function (condition) {
        console.log(condition);

        var tr = conditionTable.find("tr." + condition.name);
        if (tr.length == 0) {
            insertCallback(condition)
        } else {
            updateCallback(condition, tr);
        }
    });
});


$.get('/alerts', function (data) {
    var insertCallback = function (alert) {
        alertTable.append($(
            "<tr class='" + alert.name + "'>" +
            "<td class='name'>" + alert.name + "</td>" +
            "<td class='condition'><textarea>" + alert.condition + "</textarea></td>" +
            "<td class='active'><input type='checkbox' "  + (alert.active ? "checked" : "")  +  "></td>" +
            "<td class='action'><button class='update'>Update</button></td>" +
            "</tr>"));
    };

    var updateCallback = function (alert, tr) {
        tr.empty();
        tr.append($(
            "<td class='name'>" + alert.name + "</td>" +
            "<td class='condition'><textarea>" + alert.condition + "</textarea></td>" +
            "<td class='active'><input type='checkbox' " + (alert.active ? "checked" : "")  + "></td>" +
            "<td class='action'><button class='update'>Update</button></td>")
        );
    };

    data.forEach(insertCallback);

    pushClient.subscribeAlert(function (alert) {
        console.log(alert);

        var tr = alertTable.find("tr." + alert.name);
        if (tr.length == 0) {
            insertCallback(alert)
        } else {
            updateCallback(alert, tr);
        }
    });
});

$.get('/accounts', function (data) {
    var accountTable = $("#account tbody");

    var insertCallback = function (account) {
        accountTable.append($(
            "<tr>" +
            "<td class='date'>" + new Date(account.date).toLocaleString() + "</td>" +
            "<td class='np'>" + account.npAmount + "</td>" +
            "<td class='sx'>" + account.sxAmount + "</td>" +
            "<td class='delta'>" + (account.npAmount - account.sxAmount) + "</td>"+
            "</tr>"));
    };

    data.forEach(insertCallback);
});


pushClient.connect();