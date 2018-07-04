var CyclicArray = function (length) {
    var storage = [];
    var index = -1;
    var size = length;

    this.push = function (data) {
        if (++index == size) {
            index = 0;
        }
        storage[index] = data;
    };

    this.toArray = function () {
        return storage.slice(index, size).concat(storage.slice(0, index))
    };

    this.fill = function (data) {
        for (var i = 0; i < size; i++) {
            this.push(data);
        }
    };
}