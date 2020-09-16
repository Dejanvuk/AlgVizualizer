import { setArray, toSwap, addCurrentIndex, resetCurentIndex , addToSorted, resetSwap } from '../redux/actions';

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export default async function bubbleSort(n, dispatch, pauseTime, func) {
    let donePass = false;
    let j = 0;

    let arr = [ ...n ];

    
    while(true) {
        for(let i = 0; i < arr.length - 1 - j; i++) {
            if(func()) return;
            dispatch(resetSwap());
            dispatch(resetCurentIndex());
            dispatch(addCurrentIndex(i));// add current index i
            await sleep(pauseTime / 3);
            if(arr[i] > arr[i+1]) {
                dispatch(toSwap(i,i+1)); // add i, i+1 to swap
                await sleep(pauseTime / 3);
                let temp = arr[i+1];
                arr[i+1]  = arr[i];
                arr[i] = temp;
                dispatch(setArray(arr));
                donePass = true;
            }
        }
        dispatch(addToSorted(arr.length - 1 - j));// add to sorted j
        if(donePass == false) {
            return;
        }
        else {
            j++;
            donePass = false;
        }
        await sleep(pauseTime / 3);// pause pauseTime seconds
    }
}