import ImmutableStorage from './ImmutableStorage';
import Immutable from 'immutable';
class TestStorage extends ImmutableStorage {
  constructor(alt: TestDispatcher) {
    super(arguments);
    this.state = Immutable.List();
    this.bindActions(alt.getActions("TestAction"));
  }

  addEntity(entity) {
    this.setState(this.state.push(entity));
  }

  removeEntity(entity) {
    var index = -1;
    for (let i = 0; i < this.state.size; i++) {
      if (this.state.get(i) == entity) {
        index = i;
        break;
      }
    }

    this.setState(this.state.delete(index));
  }
}

export default TestStorage;

