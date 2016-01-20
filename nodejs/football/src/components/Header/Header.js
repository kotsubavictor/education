/*! React Starter Kit | MIT License | http://www.reactstarterkit.com/ */

import React, { Component } from 'react';
import Link from '../Link';
import Navigation from '../Navigation';

class Header extends Component {

  render() {
    return (
      <div className="Header">
        <div className="Header-container">
          <a className="Header-brand" href="/" onClick={Link.handleClick}>
            <span className="Header-brandTxt">Your Company</span>
          </a>
          <Navigation className="Header-nav" />
          <div className="Header-banner">
            <h1 className="Header-bannerTitle">React</h1>
            <p className="Header-bannerDesc">Complex web apps made easy</p>
          </div>
        </div>
      </div>
    );
  }

}

export default Header;
