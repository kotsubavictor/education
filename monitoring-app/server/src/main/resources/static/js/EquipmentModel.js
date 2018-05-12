var EquipmentModel = function (size) {
    // xmrig1 : {name: 'xmrig1', temperature: 22, updated: true, lost : 0}
    var that = this;
    var equipments = {};
    var records = new CyclicArray(size);
    var equipmentNames = [];

    var onAddedCallback = function() {};
    var onUpdatedCallback = onAddedCallback;
    var onTickCallback = onAddedCallback();

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
            equipmentNames.push(equipment.name);
            callback = onAddedCallback;
        }
        equipment.updated = true;
        equipment.lost = 0;
        equipment.temperature = data.temperature;
        equipments[equipment.name] = equipment;
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
                if (equipment.lost == 2) {
                    equipment.lost = 0;
                    equipment.temperature = 0;
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