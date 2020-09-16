import {RESET_ARRAY, NEW_ARRAY, SET_ARRAY} from '../../constants'

const initialState = [];

const MAX_NUMBER = 40;

export default function(state = initialState, action) {
    switch (action.type) {
        case RESET_ARRAY:
            return [];
        case SET_ARRAY:
            return [ ...action.payload ];
        case NEW_ARRAY:
            return [...Array(action.payload)].map(_=>Math.floor(Math.random()*80));
        default:
            return state;
    }
}