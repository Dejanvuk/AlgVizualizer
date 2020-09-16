import { ADD_USER, RESET_USER, RESET_ARRAY, SET_ARRAY, NEW_ARRAY, TO_SWAP, RESET_SWAP, ADD_CURRENT_INDEX,ADD_INDEX_AT, RESET_CURRENT_INDEX,RESET_INDEX_AT, ADD_TO_SORTED, RESET_SORTED} from '../constants';

export const newArray = size => ({
    type: NEW_ARRAY,
    payload: size
});

export const setArray = arr => ({
    type: SET_ARRAY,
    payload: arr
});

export const resetArray = () => ({
    type: RESET_ARRAY
});


export const toSwap= (index1, index2) => ({
    type: TO_SWAP,
    payload: {
        index1,
        index2
    }
});
export const resetSwap = () => ({
    type: RESET_SWAP
});


export const addCurrentIndex = (nr) => ({
    type: ADD_CURRENT_INDEX,
    payload: nr
});

export const addIndexAt = (nr,i) => ({
    type: ADD_INDEX_AT,
    payload: {
        val: nr,
        position:i
    }
});

export const resetCurentIndex = () => ({
    type: RESET_CURRENT_INDEX
});

export const resetIndexAt = (i) => ({
    type: RESET_INDEX_AT,
    payload: i
});

export const addToSorted = index => ({
    type: ADD_TO_SORTED,
    payload: index
});

export const resetSorted = () => ({
    type: RESET_SORTED
});


export const addUser = (user) => ({
    type: ADD_USER,
    payload: user
});

export const resetUser = () => ({
    type: RESET_USER
});