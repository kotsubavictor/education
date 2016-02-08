/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */

import React from 'react';

class TestComponent extends React.Component {
  flux: TestDispatcher;
  TestStorage: TestStorage;
  TestAction: TestAction;

  constructor() {
    super();
  }

  static contextTypes = {
    onSetTitle: React.PropTypes.func.isRequired,
    flux: React.PropTypes.object.isRequired,
  };

  componentWillMount() {
    //TODO koviiv - need a decorator for connecting stores to the component
    //TODO koviiv - need an abstract component
    this.flux = this.context.flux;
    this.TestStorage = this.flux.getStore("TestStorage");
    this.TestAction = this.flux.getActions("TestAction");
    // TODO koviiv - move state to the constructor. Context is not available in the constructor.
    // TODO koviiv - load data
    // TODO koviiv - double check that shouldComponentUpdate uses shallowCheck - ref check for immutable objects
    this.onChange();
  }

  componentDidMount() {
    this.TestStorage.listen(this.onChange);
  }

  componentWillUnmount() {
    this.TestStorage.unlisten(this.onChange);
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
