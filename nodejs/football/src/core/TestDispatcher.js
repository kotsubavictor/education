import Alt from "alt";
import TestAction from "../actions/TestAction";
import TestStorage from "../stores/TestStorage";

class TestDispatcher extends Alt {
  constructor() {
    super();
    this.addActions("TestAction", TestAction)
    this.addStore("TestStorage", TestStorage, this)
  }
}

export default TestDispatcher;
