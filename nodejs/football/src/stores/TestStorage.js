//import TestAction from "../actions/TestAction";
import Alt from "alt";

class TestStorage {

  constructor(alt: Alt) {
    this.data = [];
    this.bindActions(alt.getActions("TestAction"));
  }

  addEntity(entity) {
    this.data.push(entity);
  }

  removeEntity(entity) {
    var index = this.data.indexOf(removeEntity);
    if (index != -1) {
      this.data.splice(index, 1);
    }
  }
}

export default TestStorage;

