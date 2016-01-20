/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */

import React, { PropTypes, Component } from 'react';
import styles from './TestComponent.css';
import withStyles from '../../decorators/withStyles';

@withStyles(styles)
class TestComponent extends Component {

  static contextTypes = {
    onSetTitle: PropTypes.func.isRequired,
  };

  render() {
    const title = 'TestComponent';
    this.context.onSetTitle(title);

    return (
      <div className="TestComponent">
        <div>
          <h1>{title}</h1>
          <p>...</p>
          <p>bla bla bla aas</p>
        </div>
      </div>
    );
  }

}

export default TestComponent;
