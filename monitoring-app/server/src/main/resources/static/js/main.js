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

//---------------------------------------------------------------
// Init PushClient via WebSocket
var pushClient = new PushClient();
pushClient.connect();
pushClient.subscribeEquipment(function (equipment) {
    model.update(equipment);
});

pushClient.subscribeTemperature(function (temperature) {
   // todo: handle temperature update
   console.log(temperature);
});