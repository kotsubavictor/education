$(window).ready(function () {
    window.dataSize = 30;
    window.rigSize = 4;
    window.chart = new Morris.Line(generateChartData());



});

function generateChartData() {
    var result = {
        element: 'koviiv',
        data: [], /* {timing: 21312312, xmrig1: 12, xmrig2: 22} */
        xkey: 'timing',
        ykeys: [],
        labels: []
    };

    var initialDate = Date.now();
    var record;
    var rigName;

    for (var j = 0; j < rigSize; j++) {
        rigName = 'xmrig' + j;
        result.ykeys.push(rigName);
        result.labels.push(rigName);
    }


    for (var recordNumber = 0; recordNumber < dataSize; recordNumber++) {
        record = {};
        record[result.xkey] = new Date(initialDate + recordNumber * 5000).getTime();
        for (var rigNumber = 0; rigNumber < rigSize; rigNumber++) {
            rigName = result.ykeys[rigNumber];
            record[rigName] = Math.floor(30 + Math.random() * 70);
        }
        result.data.push(record);
    }

    return result;
}

function updateChart() {
    var mock = generateChartData();
    window.chart.setData(mock.data);
}









//---------------------------------------------------------------
var equipmentPushClient = new EquipmentPushClient();
equipmentPushClient.connect();
equipmentPushClient.subscribe(function (equipment) {
    showGreeting(equipment.name + ' : ' + equipment.temperature);
});

function sendName() {
    //todo: just for test
    var tmp = Math.floor(30 + Math.random() * 20);
    var equipment = {'name': $("#name").val(), 'temperature': $("#temperature").val()};
    console.log(equipment);
    equipmentPushClient.save(equipment);
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});


