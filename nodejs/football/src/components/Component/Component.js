/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */
import React from 'react';
import shallowCompare from 'react-addons-shallow-compare';

class Component extends React.Component {
  flux: TestDispatcher;
  onChange: Function;

  constructor() {
    super();
  }

  static contextTypes = {
    onSetTitle: React.PropTypes.func.isRequired,
    flux: React.PropTypes.object.isRequired,
  };

  getStores() : Array {
    return [];
  }

  getActions() : Array {
    return [];
  }

  componentWillMount() {
    var that = this;
    that.flux = that.context.flux;
    that.getStores().forEach(function(store) {
      that[store] = that.flux.getStore(store);
    });
    that.getActions().forEach(function (action) {
      that[action] = that.flux.getActions(action);
    })
    that.onChange();
  }

  componentDidMount() {
    var that = this;
    that.getStores().forEach(function(store) {
      that[store].listen(that.onChange);
    });
  }

  componentWillUnmount() {
    var that = this;
    that.getStores().forEach(function(store) {
      that[store].unlisten(that.onChange);
    });
  }

  shouldComponentUpdate(nextProps, nextState) {
    return shallowCompare(this, nextProps, nextState);
  }
}

export default Component;
