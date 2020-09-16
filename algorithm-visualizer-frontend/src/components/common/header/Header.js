import React, { Component } from 'react';

import Navbar, { Text } from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import FormControl from 'react-bootstrap/FormControl';

import { Link, withRouter } from 'react-router-dom';

import { connect } from 'react-redux';

import { resetUser } from '../../../redux/actions';

import './Header.css'

class Header extends Component {
    constructor(props) {
        super(props);
    }

    handleLogout() {
        const { resetUser, history } = this.props;
        localStorage.removeItem("JWT");
        // remove the user from storage
        resetUser();
        // REDIRECT to /home
        history.push("/login");
    }

    isLoggedIn() {
        return (this.props.user != null) ? (
            <div className="auth">
                <a href="#" className="navbar-left">
                    <img className="avi" src={this.props.user.imageUrl ? this.props.user.imageUrl: ''}/>

                    </a>
                <Navbar.Text className="userName">
                    Signed in as: <Link to="/profile">{this.props.user.name}</Link>
                </Navbar.Text>
                <Button variant="outline-success" onClick={this.handleLogout.bind(this)}>Logout</Button>
            </div>
        ) : (
                <div className="auth">
                    <Button className="loginBtn" variant="outline-success"><Link to="/login">Login</Link></Button>
                    <Button className="logoutBtn" variant="outline-success"><Link to="/signup">Signup</Link></Button>
                </div>
            );
    }

    render() {
        return (<Navbar bg="light" expand="lg">
            <Navbar.Brand >Algo Vizualizer</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link onClick={()=> {this.props.history.push("/");}}>Home</Nav.Link>
                    <NavDropdown title="Algorithms" id="basic-nav-dropdown">
                        <NavDropdown.Item href="#action/3.1">Recursive</NavDropdown.Item>
                        <NavDropdown.Item href="#action/3.2">Dynamic programming</NavDropdown.Item>
                        <NavDropdown.Item href="#action/3.3">Backtracking</NavDropdown.Item>
                        <NavDropdown.Item href="#action/3.4">Divide and conquer</NavDropdown.Item>
                        <NavDropdown.Item href="#action/3.4">Greedy</NavDropdown.Item>
                        <NavDropdown.Item href="#action/3.4">Brute Force</NavDropdown.Item>
                        <NavDropdown.Item href="#action/3.4">Randomized</NavDropdown.Item>
                    </NavDropdown>
                </Nav>
                <Form inline>
                    {this.isLoggedIn()}
                </Form>
            </Navbar.Collapse>
        </Navbar>);
    }
}


function mapStateToProps(state) {
    return {
        user: state.user
    };

}

export default connect(mapStateToProps, { resetUser })(withRouter((Header)));