var EquipmentPushClient = function () {

    var stompClient;
    var callback = function () {
    };

    this.connect = function () {
        var socket = new SockJS('/socket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/equipments', function (greeting) {
                var equipment = JSON.parse(greeting.body)
                callback(equipment);
            });
        });
    };

    this.disconnect = function() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    };

    this.subscribe  = function(tmp) {
        callback = tmp;
    };

    this.send = function (path, data) {
        stompClient.send(path, {}, JSON.stringify(data));
    };
    
    this.save = function (data) {
        this.send("/app/equipments", data);
    }
};