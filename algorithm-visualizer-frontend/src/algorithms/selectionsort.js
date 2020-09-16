import { setArray, toSwap, addCurrentIndex, resetCurentIndex , addToSorted, resetSwap } from '../redux/actions';

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export default async function insertionSort(n, dispatch, pauseTime, func) {
    let arr = [...n];
    for(let i = 0; i < arr.length - 1; i++) {
        let min = i;
        for(let j = i + 1; j < arr.length; j++) { 
            if(func()) return;
            dispatch(resetCurentIndex());
            dispatch(addCurrentIndex(j));
            await sleep(pauseTime / 3);
            if(arr[j] < arr[min]) {
                min = j;
            }
        }
        dispatch(toSwap(min,i));
        await sleep(pauseTime / 3);
        dispatch(resetSwap());
        let temp = arr[i];
        arr[i] = arr[min];
        arr[min] = temp;
        dispatch(setArray(arr));
        dispatch(addToSorted(i));
        await sleep(pauseTime / 3);

    }
}