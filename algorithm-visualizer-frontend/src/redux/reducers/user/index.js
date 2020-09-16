import { ADD_USER, RESET_USER } from '../../constants'

const initialState = null;

export default function(defaultState = initialState, action) {
    switch(action.type) {
        case ADD_USER:
            return {...action.payload};
        case RESET_USER:
            return null;
        default:
            return defaultState;
    }
}
