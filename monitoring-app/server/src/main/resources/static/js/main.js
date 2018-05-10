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





var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            var equipment = JSON.parse(greeting.body)
            showGreeting(equipment.name + equipment.temperature);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});


function a() {
    $.ajax({
        url: '/script.cgi',
        type: 'DELETE',
        success: function(result) {
            // Do something with the result
        }
    });
}


