import { ADD_TO_SORTED, RESET_SORTED } from '../../constants';

const initialState = [];

export default function(state = initialState, action) {
    switch (action.type) {
        case ADD_TO_SORTED:
            return [...state, action.payload];
        case RESET_SORTED:
            return [];
        default:
            return state;
    }
}