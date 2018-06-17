var EquipmentModel = function (size) {
    // xmrig1 : {name: 'xmrig1', temperature: 22, updated: true, lost : 0}
    var that = this;
    var equipments = {};
    var records = new CyclicArray(size);
    var equipmentNames = [];

    var onAddedCallback = function() {};
    var onUpdatedCallback = onAddedCallback;
    var onTickCallback = onAddedCallback;
    var creationDate = Date.now();

    for (var i = 0; i < size; i++) {
        records.push({timing: Date.now() - 5000 * (size - i)});
    }

    this.getData = function () {
        return equipments;
    }

    this.getRecords = function () {
        return records.toArray();
    };

    this.getEquipmentNames = function () {
        return equipmentNames;
    };

    this.onTick = function (callback) {
        onTickCallback = callback;
    }

    this.onAdded = function (callback) {
        onAddedCallback = callback;
    };

    this.onUpdated = function (callback) {
        onUpdatedCallback = callback;
    };

    this.update = function(data) {
        var equipment = equipments[data.name];
        var callback = onUpdatedCallback;
        if(equipment === undefined) {
            equipment = $.extend({}, data);
            equipments[equipment.name] = equipment;
            equipmentNames.push(equipment.name);
            callback = onAddedCallback;
        } else {
            equipment.temperature = data.temperature;
            equipment.online = data.online;
            equipment.humidity = data.humidity;
        }
        equipment.updated = true;
        equipment.lost = 0;

        callback(equipment);
    };

    this.log = function () {
        var equipment;
        var record = {timing: Date.now()};
        for(var key in equipments) {
            equipment = equipments[key];
            if(equipment.updated) {
                equipment.updated = false;
                equipment.lost = 0;
            } else {
                equipment.lost++;
                if (equipment.lost == 4) {
                    equipment.lost = 0;
                    equipment.temperature = 0;
                    equipment.online = false;
                    equipment.humidity = 0;
                }
            }
            record[equipment.name] = equipment.temperature;
        }
        records.push(record);
    };

    setInterval(function () {
        that.log();
        onTickCallback();
    }, 5000);
};