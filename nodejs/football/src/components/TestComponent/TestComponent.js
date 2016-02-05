/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */

import React from 'react';

class TestComponent extends React.Component {

    constructor() {
      super();
    }

    static contextTypes = {
    onSetTitle: React.PropTypes.func.isRequired,
    flux: React.PropTypes.object.isRequired,
  };

  componentWillMount() {
    // TODO koviiv - how to specify a type of the variable. For instance, this.flux : TestDispatcher
    this.flux = this.context.flux;
    this.TestStorage = this.flux.getStore("TestStorage");
    this.TestAction = this.flux.getActions("TestAction");
    // TODO koviiv - move state to the constructor. Context is not available in the constructor.
    this.setState(this.TestStorage.getState());
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

    console.log("Storage values:");
    console.log(this.TestStorage.getState());
    console.log("Components values:");
    console.log(this.state);

    var data: Array = this.state.data;
    var list = data.map((value, number)=> {
      return <div>{number})  {value}</div>
    });

    return (
      <div className="TestComponent">
        <div>
          <h1>{title}</h1>
            <div><input ref="koviiv" type="text" /></div>
            <div><input type="button" value="Add" onClick={this.handleClick}/></div>
            <div>{list}</div>
        </div>
      </div>
    );
  }

  handleClick = () => {
    var value = this.refs.koviiv.value;
    console.log("Input value:");
    console.log(value);
    this.TestAction.addEntity(value);
  }

  onChange = (data) => {
    this.setState(data);
  }
}

export default TestComponent;
