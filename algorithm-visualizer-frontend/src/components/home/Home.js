import React, {Component} from 'react';

import Searchbar from './searchbar/Searchbar';
import AlgoList from './algolist/AlgoList';

import './Home.css';

class Home extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="home">
                <Searchbar />
                <AlgoList/>
            </div>
        );
    }
}

export default Home;