import Immutable from 'immutable';
class ImmutableStorage {
  state: Object;

    constructor() {
      // TODO koviiv - There is a problem with bootstrap function
      // It saves immutable instance to other instance.
      this.on('bootstrap', (state) => {
        this.state = state;
      });
    }

    static config = {
    setState(currentState, nextState) {
      this.state = nextState;
      return this.state;
    },

    getState(currentState) {
      return currentState;
    },

    onSerialize(state) {
      return state.toJS();
    },

    onDeserialize(data) {
      this.state = Immutable.fromJS(data);
      return this.state;
    }
  };
}

export default ImmutableStorage;
