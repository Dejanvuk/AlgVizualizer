import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { ALGORITHMS } from '../../../constants/index';
import './AlgoList.css';

class AlgoList extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const algorithms = ALGORITHMS.map((algorithm, index) => (
            <li className="algorithm" key={index}>
                <div className="li-wrapper">
                    <Link className="thumbnail" to={"algorithm/" + algorithm.name.trim().toLowerCase()}> 
                        <img src={algorithm.imageURL} />
                    </Link>

                    <div className="info">
                        <h3>
                            {algorithm.name}
                        </h3>
                    </div>
                </div>
            </li>
        ));


        return (
            <div id="algolist">
                <ul className="list">
                    {algorithms}
                </ul>
            </div>
        );
    }
}

export default AlgoList;