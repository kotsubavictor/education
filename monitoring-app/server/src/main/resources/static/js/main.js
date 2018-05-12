//---------------------------------------------------------------
// Chart
var chart = null;
var model = new EquipmentModel();

model.onAdded(function () {
    console.log('Added');
    var names = model.getEquipmentNames();
    var data = {
        element: 'koviiv',
        data: model.getRecords(), /* {timing: 21312312, xmrig1: 12, xmrig2: 22} */
        xkey: 'timing',
        ykeys: names,
        labels: names
    };

    $("#koviiv").html('');
    chart = new Morris.Line(data);
});

model.onUpdated(function () {
    console.log('Updated');
});

model.onTick(function () {
    chart.setData(model.getRecords());
});

//---------------------------------------------------------------
// Init EquipmentPush via WebSocket
var equipmentPushClient = new EquipmentPushClient();
equipmentPushClient.connect();
equipmentPushClient.subscribe(function (equipment) {
    model.update(equipment);
});

var sendName  = function () {
    //todo: just for test
    var equipment = {'name': $("#name").val(), 'temperature': $("#temperature").val()};
    console.log(equipment);
    equipmentPushClient.save(equipment);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});