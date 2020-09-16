import { TO_SWAP, RESET_SWAP } from '../../constants'

const initialState = {
    index1: null,
    index2: null
};

export default function(defaultState = initialState, action) {
    switch(action.type) {
        case TO_SWAP:
            return {...defaultState, index1: action.payload.index1, index2: action.payload.index2};
        case RESET_SWAP:
            return {
                index1: null,
                index2: null
            }
        default:
            return defaultState;
    }
}
