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
        "<tr class='" + equipment.name + (equipment.online ? "" : " table-danger") +"'>" +
        "<td class='name'>" + equipment.name + "</td>" +
        "<td class='online'>" + equipment.online + "</td>" +
        "<td class='temperature'>" + equipment.temperature + "</td>" +
        "<td class='humidity'>" + equipment.humidity + "</td>" +
        "</tr>"));
});

model.onUpdated(function (equipment) {
    var tr = equipmentTable.find("tr."+equipment.name);
    tr.removeClass();
    tr.addClass(equipment.name);

    if (equipment.temperature >= 70) {
        tr.addClass("table-warning");
    }

    if (!equipment.online) {
        tr.addClass("table-danger");
    }

    tr.children().each(function (index, td) {
        td.innerText = equipment[td.className];
    });
});

model.onTick(function () {
    chart.setData(model.getRecords());
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

pushClient.subscribeRele(function (rele) {
    console.log(rele);
    reles[rele.name] = rele;

    var tr = releTable.find("tr."+rele.name);
    if (tr.length == 0){
        releTable.append($(
            "<tr class='" + rele.name + "'>" +
            "<td class='name'>" + rele.name + "</td>" +
            "<td class='power'>" + rele.power + "</td>" +
            "</tr>"));
    } else {
        tr.removeClass();
        tr.addClass(rele.name);

        if (rele.power) {
            tr.addClass("table-success");
        }

        tr.children().each(function (index, td) {
            td.innerText = rele[td.className];
        });
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

pushClient.connect();