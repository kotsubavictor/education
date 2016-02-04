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

}

export default TestStorage;

