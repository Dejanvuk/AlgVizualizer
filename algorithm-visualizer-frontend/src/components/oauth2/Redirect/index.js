import React, { Component } from 'react';

import { Redirect, withRouter } from 'react-router-dom';

import queryString from 'query-string';

import { connect } from 'react-redux';
import { addUser } from '../../../redux/actions';

import { BASE_URL } from '../../../constants';

class RedirectOauth2 extends Component {
    constructor(props) {
        super(props);
        this.state = {
            token: "",
            error: ""
        }
    }

    async componentWillMount() {
        const values = queryString.parse(this.props.location.search);
        const token = values.jwt;
        const error = values.error;

        if (token) {
            await this.setState({ token: token, error: error });
            localStorage.setItem("JWT", token);
            await fetch(BASE_URL + '/user/me', {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': token
                }
            }).then(response => response.json()).then(user => {
                this.props.addUser(user);
            });
        }
    }

    render() {
        const { token, error } = this.state;
        if (token) {
            return <Redirect to={{
                pathname: "/",
                state: { from: this.props.location }
            }} />;
        }
        else {
            return <Redirect to={{
                pathname: "/login",
                state: {
                    from: this.props.location
                }
            }} />;
        }
    }
}

export default connect(null, { addUser })(withRouter(RedirectOauth2));