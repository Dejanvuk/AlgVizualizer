import React, { Component } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { BASE_URL } from '../../constants';

import './ForgotPassword.css';

class ForgotPassword extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            sent: false
        }
    }

    async handleSubmit(e) {
        e.preventDefault();
        const { email } = this.state;
        await fetch(BASE_URL + '/api/user/reset-password?usernameOrEmail=' + email
        ).then(response => {
            this.setState({sent: true});
        });
    }

    handleChange(e) {
        let email = e.target.value;
        this.setState({ email });
    }

    render() {
        const { sent } = this.state;
        return (!sent) ? (
            <div className="forgotPassword-container">
                <h1> Forgot Password </h1>
                <div className="forgotPassword-content">
                    <Form onSubmit={this.handleSubmit.bind(this)}>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label className="loginLabels" >Email address</Form.Label>
                            <Form.Control name="usernameOrEmail" onChange={this.handleChange.bind(this)} placeholder="Enter Username or Email" />
                        </Form.Group>
                        <Button id="loginBtn" variant="primary" type="submit">
                            Submit
                        </Button>
                    </Form>
                </div>
            </div>
        ) : (
            <div className="forgotPassword-container">
                <h1>Please check your email address for your new password!</h1>
            </div>
        );
    }
}

export default ForgotPassword;