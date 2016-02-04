/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */

import React from 'react';
import TestDispatcher from './../../core/TestDispatcher';
import TestAction from './../../actions/TestAction';

class TestComponent extends React.Component {

    constructor() {
      super();
    }

    static contextTypes = {
    onSetTitle: React.PropTypes.func.isRequired,
    flux: React.PropTypes.object.isRequired,
  };

  componentWillMount() {
    this.setState(this.context.flux.getStore("TestStorage").getState());
  }

  componentDidMount() {
    this.context.flux.getStore("TestStorage").listen(this.onChange.bind(this));
  }

  render() {
    const title = 'TestComponent';
    this.context.onSetTitle(title);

    var data: Array = this.state.data;
    console.log("Storage values:");
    console.log(this.context.flux.getStore("TestStorage").getState());
    console.log("Components values:");
    console.log(this.state);

    var list = data.map((value, number)=> {
      return <div>{number})  {value}</div>
    });

    return (
      <div className="TestComponent">
        <div>
          <h1>{title}</h1>
            <div><input ref="koviiv" type="text" /></div>
            <div><input type="button" value="Add" onClick={this.handleClick.bind(this)}/></div>
            <div>{list}</div>
        </div>
      </div>
    );
  }

  handleClick() {
    var value = this.refs.koviiv.value;
    console.log("Input value:");
    console.log(value);
    var flux: TestDispatcher =  this.context.flux;
    var action: TestAction = flux.getActions("TestAction");
    action.addEntity(value);

  }

  onChange(data) {
    this.setState(data);
  }
}

export default TestComponent;
