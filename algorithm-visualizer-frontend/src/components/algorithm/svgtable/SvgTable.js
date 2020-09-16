import React, { Component } from 'react';

import { connect } from 'react-redux';

import PropTypes from 'prop-types'

import './SvgTable.css';

class SvgTable extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { array, index, swap, sortedArray } = this.props;

        const width = array.length > 100 ? 4 :
            array.length > 50 ? 8 :
                array.length > 10 ? 16 : 80;
        const margin = array.length > 100 ? 1 :
            array.length > 50 ? 2 :
                array.length > 10 ? 4 : 20;
        const bracket = array.length > 100 ? 200 :
            array.length > 50 ? 100 :
                array.length > 10 ? 50 : 10;

        let startPoint = (1000 / bracket) * ((bracket - array.length) / 2);

        return (
            <div className="container d-flex justify-content-center">
                <svg height="800px" width="1200px">
                    {
                        array.length ? array.map((val, i) => {
                            const x = i === 0 ? startPoint + margin / 2 : (i * width + i * margin + startPoint + margin / 2);
                            const y = (400 - (3 * val));
                            
                            const rgb = (swap.index1 == i || swap.index2 == i) ? 'rgb(50,50,50)'
                                : index.includes(i) ? 'rgb(200,150,44)'
                                    : sortedArray.includes(i) ? 'rgb(20,70,25)' : 'rgb(76,163,221)';
                            return (
                                <g key={i} transform={`translate(${x},${y})`}>
                                    <rect height={val * 3 + "px"} width={width} style={{ fill: rgb }}>

                                    </rect>
                                </g>
                            )
                        })
                            : null
                    }
                </svg>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        array: state.array,
        index: state.index,
        swap: state.swap,
        sortedArray: state.sortedArray
    };
};

SvgTable.propTypes = {
    array: PropTypes.array.isRequired,
    index: PropTypes.array.isRequired,
    swap: PropTypes.object.isRequired,
    sortedArray: PropTypes.array.isRequired
};

export default connect(mapStateToProps, null)(SvgTable);