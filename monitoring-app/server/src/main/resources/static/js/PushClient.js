var PushClient = function () {

    var stompClient;
    var equipmentCallback = function () {
    };
    var temperatureCallback = equipmentCallback;
    var releCallback = equipmentCallback;
    var conditionCallback = equipmentCallback;

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

            stompClient.subscribe('/topic/reles', function (response) {
                var rele = JSON.parse(response.body);
                releCallback(rele);
            });

            stompClient.subscribe('/topic/conditions', function (response) {
                var condition = JSON.parse(response.body);
                conditionCallback(condition);
            });
        });
    };

    this.disconnect = function () {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    };

    this.subscribeEquipment = function (tmp) {
        equipmentCallback = tmp;
    };

    this.subscribeTemperature = function (tmp) {
        temperatureCallback = tmp;
    };

    this.subscribeRele = function (tmp) {
        releCallback = tmp;
    };

    this.subscribeCondition = function (tmp) {
        conditionCallback = tmp;
    };

    this.send = function (path, data) {
        stompClient.send(path, {}, JSON.stringify(data));
    };

    this.saveEquipment = function (data) {
        this.send("/app/equipments", data);
    };

    this.saveCondition = function (data) {
        this.send("/app/conditions", data);
    };
};