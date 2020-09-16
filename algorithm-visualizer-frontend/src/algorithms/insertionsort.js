import { setArray, toSwap, addCurrentIndex, resetCurentIndex , addToSorted, resetSwap } from '../redux/actions';

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export default async function insertionSort(n, dispatch, pauseTime, func) {
    let pos;
    let arr = [...n];
    
    for(let i = 1; i < arr.length; i++) {
        pos = i;
        dispatch(resetSwap());
        dispatch(resetCurentIndex());
        dispatch(addCurrentIndex(pos));
        await sleep(pauseTime / 3);
        for(let j = i - 1; j >= 0; j--) {
            if(func()) return;
            if(arr[pos] < arr[j]) {
                dispatch(toSwap(pos,j));
                await sleep(pauseTime / 3);
                let temp = arr[j];
                arr[j] = arr[pos];
                arr[pos] = temp;
                dispatch(setArray(arr));
                pos = j;
            }
        }
        dispatch(addToSorted(i - 1));
        await sleep(pauseTime / 3);
    }
}