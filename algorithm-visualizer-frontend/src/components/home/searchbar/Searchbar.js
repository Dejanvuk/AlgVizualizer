import React, {Component} from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';

const Searchbar = (props) => {
    return (
        <div className="container h-100 d-flex justify-content-center h-100">
                <Form className="searchbar">
                    <InputGroup className="mb-3">
                        <FormControl
                            placeholder="Search algorithm..."
                            aria-label="Search algorithm"
                            aria-describedby="basic-addon2"
                        />
                        <InputGroup.Append>
                            <Button variant="outline-secondary">Go</Button>
                        </InputGroup.Append>
                    </InputGroup>
                </Form>
            </div>
    );
};

export default Searchbar;
