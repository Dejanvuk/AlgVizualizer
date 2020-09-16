import React, { Component } from 'react';

import './Login.css';

import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Form, { Group, Label, Control, Text, Check } from 'react-bootstrap/Form';

import { connect } from 'react-redux';
import { addUser } from '../../redux/actions';

import { withRouter, Link } from 'react-router-dom';

import queryString from 'query-string';

import { authorizationLink, redirectUri } from '../../constants';

import { BASE_URL } from '../../constants';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            item: {
                usernameOrEmail: "",
                password: "",
                error: ""
            }
        }
    }

    componentDidMount() {
        if (this.props.location.state) {
            const error = queryString.parse(this.props.location.state.from.search).error;
            this.setState({ error });
        }
    }

    handleChange(e) {
        let item = { ...this.state.item };
        item[e.target.name] = e.target.value;
        this.setState({ item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;
        await fetch(BASE_URL + '/auth/login',
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ usernameOrEmail: item.usernameOrEmail, password: item.password })
            }
        ).then(response => { return response.headers.get("Authorization") }).then(jwt => {
            localStorage.setItem("JWT", jwt);
        });
        await fetch(BASE_URL + '/user/me', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem("JWT")
            }
        }).then(response => response.json()).then(user => {
            this.props.addUser(user);
            this.props.history.push("/");
        });
    }

    render() {
        return (
            <div className="login-container">
                <h1>Login</h1>
                <div className="login-content">
                    <Form onSubmit={this.handleSubmit.bind(this)}>
                        <p id="errorText" className="hint-text">{this.state.error}</p>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label className="loginLabels" >Email address</Form.Label>
                            <Form.Control name="usernameOrEmail" onChange={this.handleChange.bind(this)} placeholder="Enter Username or Email" />
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label className="loginLabels" >Password</Form.Label>
                            <Form.Control type="password" name="password" onChange={this.handleChange.bind(this)} placeholder="*******" />
                        </Form.Group>

                        <Form.Group controlId="formBasicCheckbox">
                            <Form.Check className="loginLabels" type="checkbox" label="Remember me" />
                            <Form.Label>
                                <Link className="" to="/forgotpassword">
                                    Forgot password?
                                </Link>
                            </Form.Label>
                        </Form.Group>

                        <Button id="loginBtn" variant="primary" type="submit">
                            Submit
                        </Button>
                        <div className="d-flex">
                            <hr className="my-auto flex-grow-1"></hr>
                            <div className="px-4">or</div>
                            <hr className="my-auto flex-grow-1"></hr>
                        </div>
                        <p className="hint-text">Sign up with your social media account or email address</p>
                        <ButtonGroup className="social-btn text-center btn-group-vertical btn-group-justified">
                            <Button id="btnFacebook" variant="" className="btnOauth2" onClick={() => { window.location.href = authorizationLink + "facebook?redirect_uri=" + redirectUri }}>
                                Facebook
                            </Button>
                            <Button id="btnGoogle" variant="" className="btnOauth2" onClick={() => { window.location.href = authorizationLink + "google?redirect_uri=" + redirectUri }}>
                                Google
                            </Button>
                            <Button id="btnGithub" variant="" className="btnOauth2" onClick={() => { window.location.href = authorizationLink + "github?redirect_uri=" + redirectUri }}>
                                Github
                            </Button>
                        </ButtonGroup>
                    </Form>
                </div>
            </div>
        );
    }
}

export default connect(null, { addUser })(withRouter(Login));