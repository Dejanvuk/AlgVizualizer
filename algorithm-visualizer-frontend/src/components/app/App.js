import React, { Component } from 'react';
import './App.css';

import Header from '../common/header/Header';
import Algorithm from '../algorithm/Algorithm';
import RedirectOauth2 from '../oauth2/Redirect';

import { Route, Switch } from 'react-router-dom';

class DynamicImport extends Component {
  state = {
    component: null
  }
  componentDidMount () {
    this.props.load()
      .then((component) => {
        this.setState(() => ({
          component: component.default ? component.default : component
        }))
      })
  }
  render() {
    return this.props.children(this.state.component)
  }
}

const Home = (props) => (
  <DynamicImport load={() => import(/* webpackChunkName: "Home" */ '../home/Home')}>
    {(Component) => Component === null
      ? <p>Loading</p>
      : <Component {...props} />}
  </DynamicImport>
)

const Login = (props) => (
  <DynamicImport load={() => import(/* webpackChunkName: "Login" */'../login/Login')}>
    {(Component) => Component === null
      ? <p>Loading</p>
      : <Component {...props} />}
  </DynamicImport>
)

const Signup = (props) => (
  <DynamicImport load={() => import(/* webpackChunkName: "Signup" */'../signup/Signup')}>
    {(Component) => Component === null
      ? <p>Loading</p>
      : <Component {...props} />}
  </DynamicImport>
)


const ForgotPassword = (props) => (
  <DynamicImport load={() => import(/* webpackChunkName: "ForgotPassword" */'../forgotPassword/ForgotPassword')}>
    {(Component) => Component === null
      ? <p>Loading</p>
      : <Component {...props} />}
  </DynamicImport>
)


class App extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="App">
        <Header />
        <div id="main">
          <Switch>
            <Route path="/" exact={true} component={Home} />
            <Route path="/login" exact={true} component={Login} />
            <Route path="/signup" exact={true} component={Signup} />
            <Route path='/algorithm/:id' component={Algorithm} />
            <Route path='/oauth2/redirect' exact={true} component={RedirectOauth2} />
            <Route path='/forgotpassword' exact={true} component={ForgotPassword} />
          </Switch>
        </div>
      </div>
    );
  }
}


export default App;
