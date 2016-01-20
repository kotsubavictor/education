import { ReduceStore } from 'flux/utils';
import Immutable from 'immutable';
import Dispatcher from '../core/Dispatcher.js';

class TestStorage extends ReduceStore {

  getInitialState() : Immutable.OrderedMap<String, Number> {
    return Immutable.OrderedMap();
  }

  reduce(state: Immutable.Map<String, Number>, action: Object) : Immutable.Map<String, Number> {
    // todo handle action and update a store
    return state;
  }
}

export default new TestStorage(Dispatcher);

