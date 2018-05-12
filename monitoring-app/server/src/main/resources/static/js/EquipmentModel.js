var EquipmentModel = function () {
    // xmrig1 : {name: 'xmrig1', temperature: 22, updated: true, lost : 0}
    var that = this;
    var equipments = {};
    var records = []

    var onAdded = function() {};
    var onUpdated = onAdded;


    this.update = function(data) {
        var equipment = equipments[data.name];
        if(equipment === undefined) {
            equipment = $.extend({}, data);
        }
        equipment.updated = true;
        equipment.lost = 0;
        equipment.temperature = data.temperature;
        onUpdated(equipment);
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
    }, 5000);
};