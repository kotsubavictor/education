/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */
import Component from './../Component';
import React from 'react';

class TestComponent extends Component  {
  flux: TestDispatcher;
  TestStorage: TestStorage;
  TestAction: TestAction;

  constructor() {
    super();
  }

  getStores() : Array {
    return ["TestStorage"];
  }

  getActions() : Array {
    return ["TestAction"];
  }

  render() {
    const title = 'TestComponent';
    this.context.onSetTitle(title);

    var data: Immutable.Collection = this.state.data;
    if (data) {
      var list = data.map((value, number)=> {
        return <div key={number}><input type="button" value="Remove" onClick={this.removeEntry(value)}/> {number})  {value}</div>
      });
    }

    return (
      <div className="TestComponent">
        <div>
          <h1>{title}</h1>
            <div><input ref="entry" type="text" /></div>
            <div><input type="button" value="Add" onClick={this.addEntry}/></div>
            <div>{list}</div>
        </div>
      </div>
    );
  }

  addEntry = () => {
    this.TestAction.addEntity(this.refs.entry.value);
  }

  onChange = () => {
    var state = {
      data: this.TestStorage.getState()
    };
    this.setState(state);
  }

  removeEntry = (entry) => {
    return () => {
      this.TestAction.removeEntity(entry)
    };
  }
}

export default TestComponent;
