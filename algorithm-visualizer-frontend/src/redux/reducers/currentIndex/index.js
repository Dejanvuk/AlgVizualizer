import { ADD_CURRENT_INDEX, RESET_CURRENT_INDEX, RESET_INDEX_AT, ADD_INDEX_AT } from '../../constants';

const initialState = [];

export default function(state = initialState, action) {
    let arr = [...state];
    switch(action.type) {
        case ADD_CURRENT_INDEX:
            return [...state, action.payload];
        case ADD_INDEX_AT:
            arr[action.payload.position] = action.payload.val;
            return arr;
        case RESET_CURRENT_INDEX:
            return [];
        case RESET_INDEX_AT:
            arr[action.payload] = null;
            return arr;
        default:
            return state;
    }
}