var TemperatureModel = function (size) {
    // xmrig1 : {name: 'xmrig1', temperature: 22, updated: true, lost : 0}
    var that = this;
    var temperatures = {};
    var records = new CyclicArray(size);
    var equipmentNames = [];

    var onAddedCallback = function() {};
    var onUpdatedCallback = onAddedCallback;

    this.getRecords = function () {
        return records.toArray();
    };

    this.getEquipmentNames = function () {
        return equipmentNames;
    };

    this.onAdded = function (callback) {
        onAddedCallback = callback;
    };

    this.onUpdated = function (callback) {
        onUpdatedCallback = callback;
    };

    this.update = function(collection) {
        var temperature = undefined;
        var callback = onUpdatedCallback;
        var key;
        collection.forEach(function (value) {
            var record = {timing: value.date};
            value.temperatures.forEach(function (data) {
                temperature = temperatures[data.name];
                if(temperature === undefined) {
                    temperature = $.extend({}, data);
                    equipmentNames.push(temperature.name);
                    callback = onAddedCallback;
                }
                temperature.updated = true;
                temperature.lost = 0;
                temperature.max = data.max;
                temperatures[temperature.name] = temperature;
            });

            for(key in temperatures) {
                temperature = temperatures[key];
                if(temperature.updated) {
                    temperature.updated = false;
                    temperature.lost = 0;
                } else {
                    temperature.lost++;
                    if (temperature.lost == 1) {
                        temperature.lost = 0;
                        temperature.max = 0;
                    }
                }
                record[temperature.name] = temperature.max;
            }
            records.push(record);
        });


        callback(that.getRecords());
    };
};