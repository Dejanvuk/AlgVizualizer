import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import SvgTable from './svgtable/SvgTable';

import { connect } from 'react-redux';
import { newArray, resetCurentIndex, resetSwap, resetSorted } from '../../redux/actions';

import bubbleSort from '../../algorithms/bubblesort';
import insertionSort from '../../algorithms/insertionsort';
import selectionSort from '../../algorithms/selectionsort';
import mergeSort from '../../algorithms/mergesort';
import quickSort from '../../algorithms/quicksort';

import './Algorithm.css'

class Algorithm extends Component {
    constructor(props) {
        super(props);
        this.speedSliderDefault = 50; // ms
    }

    componentDidMount() {
        this.props.newArray(40);
        this.props.resetArrays();
        this.algorithmOn = false;
    }

    componentWillUnmount() {
        this.algorithmOn = true;
    }

    handleChange(e) {
        //this.sliderDefaultValue = e.target.value;
        if (e.target.name == "sliderRange") this.props.newArray(parseInt(e.target.value));
        else if (e.target.name == "sliderSpeed") this.speedSliderDefault = parseInt(e.target.value);
    }

    stopAlgorithm() {
        return this.algorithmOn;
    }

    startAlgorithm() {
        switch (this.props.match.params.id) {
            case "bubble sort":
                this.props.bubbleSort(this.props.array, this.speedSliderDefault, this.stopAlgorithm.bind(this));
                break;
            case "insertion sort":
                this.props.insertionSort(this.props.array, this.speedSliderDefault, this.stopAlgorithm.bind(this));
                break;
            case "merge sort":
                this.props.mergeSort(this.props.array, this.speedSliderDefault, this.stopAlgorithm.bind(this));
                break;
            case "selection sort":
                this.props.selectionSort(this.props.array, this.speedSliderDefault, this.stopAlgorithm.bind(this));
                break;
            case "quick sort":
                this.props.quickSort(this.props.array, this.speedSliderDefault, this.stopAlgorithm.bind(this));
                break;
            default:
                break;
        }
    }

    render() {
        return (
            <div className="AlgorithmTable">
                <div className="tableHeader">
                    <Button id="btnBack" variant="outline-primary" onClick={() => { this.algorithmOn = true; this.props.history.push('/'); }}>Back</Button>
                    <label htmlFor="customRange1">Range</label>
                    <input name="sliderRange" id="sliderForRange" type="range" onChange={this.handleChange.bind(this)} min="10" max="200" className="custom-range" />
                    <label htmlFor="customRange1">Animation Speed</label>
                    <input name="sliderSpeed" id="sliderForSpeed" type="range" onChange={this.handleChange.bind(this)} min="1" max="2000" className="custom-range" />
                    <Button id="btnStart" variant="danger" onClick={this.startAlgorithm.bind(this)}>Start</Button>
                </div>
                <SvgTable></SvgTable>
            </div>
        );
    }
}

const mapDispatchToProps = dispatch => ({
    bubbleSort: (array, pauseTime, func) => {
        bubbleSort(array, dispatch, pauseTime, func);
    },
    insertionSort: (array, pauseTime, func) => {
        insertionSort(array, dispatch, pauseTime, func);
    },
    mergeSort: (array, pauseTime, func) => {
        mergeSort(array, dispatch, pauseTime, func);
    },
    selectionSort: (array, pauseTime, func) => {
        selectionSort(array, dispatch, pauseTime, func);
    },
    quickSort: (array, pauseTime, func) => {
        quickSort(array, dispatch, pauseTime, func);
    },
    newArray: (size) => { dispatch(newArray(size)) },
    resetArrays: () => {
        dispatch(resetSwap());
        dispatch(resetCurentIndex());
        dispatch(resetSorted());
    }
});

function mapStateToProps(state) {
    return {
        array: state.array
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Algorithm));