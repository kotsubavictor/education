//---------------------------------------------------------------
// Model + Chart
var chart = null;
var model = new EquipmentModel(200);

model.onAdded(function () {
    var names = model.getEquipmentNames();
    var data = {
        element: 'koviiv',
        data: model.getRecords(),
        xkey: 'timing',
        ykeys: names,
        labels: names,
        pointSize: 0,
        ymin: 40,
        ymax: 90,
        xLabels: '5sec',
        goals: [70, 75],
        goalLineColors: ['#FFFF00', '#ff0000'],
        goalStrokeWidth: 4,
        eventStrokeWidth: 1,
        hideHover: 'auto'
    };

    $("#koviiv").html('');
    chart = new Morris.Line(data);
});

model.onUpdated(function () {
});

model.onTick(function () {
    chart.setData(model.getRecords());
});



var tempChart = null;
var tempModel = new TemperatureModel(288);

tempModel.onAdded(function (records) {
    var names = tempModel.getEquipmentNames();
    var data = {
        element: 'koviiv1',
        data: records,
        xkey: 'timing',
        ykeys: names,
        labels: names,
        pointSize: 0,
        ymin: 40,
        ymax: 90,
        xLabels: '5sec',
        goals: [70, 75],
        goalLineColors: ['#FFFF00', '#ff0000'],
        goalStrokeWidth: 4,
        eventStrokeWidth: 1,
        hideHover: 'auto'
    };

    $("#koviiv1").html('');
    tempChart = new Morris.Line(data);
});

tempModel.onUpdated(function (records) {
    tempChart.setData(records);
});

$.get('/temperatures', function (data) {
    tempModel.update(data);
});

//---------------------------------------------------------------
// Init PushClient via WebSocket
var pushClient = new PushClient();
pushClient.connect();
pushClient.subscribeEquipment(function (equipment) {
    model.update(equipment);
});

pushClient.subscribeTemperature(function (temperature) {
    console.log(temperature);
    tempModel.update([temperature]);
});