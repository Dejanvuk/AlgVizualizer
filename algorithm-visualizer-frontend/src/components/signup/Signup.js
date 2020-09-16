import React, { Component } from 'react';

import Form, { Group, Label, Control, Text, Check } from 'react-bootstrap/Form';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Button from 'react-bootstrap/Button';

import { Link } from 'react-router-dom';

import { authorizationLink, redirectUri } from '../../constants';

import { withRouter } from 'react-router-dom';

import { BASE_URL } from '../../constants';

import './Signup.css';

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            item: {
                name: "",
                username: "",
                email: "",
                password: ""
            }
        }
    }

    handleChange(event) {
        let item = { ...this.state.item };
        item[event.target.name] = event.target.value;
        this.setState({ item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;
        await fetch(BASE_URL + '/auth/sign-up',
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item)
            }
        ).then(() => {this.props.history.push("/");});
    }

    render() {
        return (
            <div className="login-container">
                <Form onSubmit={this.handleSubmit.bind(this)}>
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
                    <div className="d-flex">
                        <hr className="my-auto flex-grow-1"></hr>
                        <div className="px-4">or</div>
                        <hr className="my-auto flex-grow-1"></hr>
                    </div>
                    <h2>Create an Account</h2>
                    <Form.Group>
                        <Form.Control placeholder="Name" name="name" onChange={this.handleChange.bind(this)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Control placeholder="Username" name="username" onChange={this.handleChange.bind(this)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Control type="email" placeholder="Email" name="email" onChange={this.handleChange.bind(this)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Control type="password" placeholder="*******" name="password" required="required" onChange={this.handleChange.bind(this)} />
                    </Form.Group>
                    <Form.Group>
                        <Button variant="primary" className="btn btn-success btn-lg btn-block signup-btn" type="submit">
                            Submit
                        </Button>
                    </Form.Group>
                </Form>
                <div className="text-center">Already have an account? Log in <Link to="/login">here</Link></div>
            </div>
        );
    }
}

export default withRouter(Signup);