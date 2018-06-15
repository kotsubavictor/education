var PushClient = function () {

    var stompClient;
    var equipmentCallback = function () {
    };
    var temperatureCallback = equipmentCallback;
    var releCallback = equipmentCallback;

    this.connect = function () {
        var socket = new SockJS('/socket');
        stompClient = Stomp.over(socket);
        stompClient.debug = null;
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/equipments', function (response) {
                var equipment = JSON.parse(response.body);
                equipmentCallback(equipment);
            });

            stompClient.subscribe('/topic/temperatures', function (response) {
                var temperatureSnapshot = JSON.parse(response.body);
                temperatureCallback(temperatureSnapshot);
            });

            stompClient.subscribe('/topic/rele', function (response) {
                var rele = JSON.parse(response.body);
                releCallback(rele);
            });
        });
    };

    this.disconnect = function() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    };

    this.subscribeEquipment  = function(tmp) {
        equipmentCallback = tmp;
    };

    this.subscribeTemperature  = function(tmp) {
        temperatureCallback = tmp;
    };

    this.subscribeRele  = function(tmp) {
        releCallback = tmp;
    };

    this.send = function (path, data) {
        stompClient.send(path, {}, JSON.stringify(data));
    };
    
    this.saveEquipment = function (data) {
        this.send("/app/equipments", data);
    }
};