import { setArray, toSwap, addCurrentIndex, resetCurentIndex , resetIndexAt, addIndexAt, addToSorted, resetSwap } from '../redux/actions';

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export default async function quickSort(n, dispatch, pauseTime, func) {
    let arr = [...n];
    sort(arr,0,arr.length - 1, dispatch, pauseTime, func);
}

async function sort(n,l,r, dispatch, pauseTime, func) { 
    let i = l, j = r, p = n[Math.floor((l+r)/2)];

    while(i <= j) {
        dispatch(resetCurentIndex());
        if(func()) return;
        while(n[i] < p) {
            dispatch(resetIndexAt(0)); // left index will be stored at position 0 of the index array
            dispatch(addIndexAt(i,0)); 
            await sleep(pauseTime / 3);
            i++;
        }
        while(n[j] > p) {
            dispatch(resetIndexAt(1)); // // right index will be stored at position 1 of the index array
            dispatch(addIndexAt(j,1));
            await sleep(pauseTime / 3);
            j--;
        }
        if(i <= j) {
            let temp = n[i];
            n[i] = n[j];
            n[j] = temp;
            dispatch(setArray(n));
            await sleep(pauseTime / 3);
            dispatch(toSwap(i,j));
            await sleep(pauseTime / 3);
            dispatch(resetSwap());
            i++;
            j--;
            dispatch(resetCurentIndex());
            dispatch(addCurrentIndex(i));
            dispatch(addCurrentIndex(j));
        }
    }
    dispatch(setArray(n));
    await sleep(pauseTime / 3);
   
    if(j > l) await sort(n, l, j, dispatch, pauseTime, func);
    else {dispatch(addToSorted(j));dispatch(addToSorted(j+1)); await sleep(pauseTime / 3);}
    if(i < r) await sort(n, i, r, dispatch, pauseTime, func);
    else {dispatch(addToSorted(i));dispatch(addToSorted(i+1));await sleep(pauseTime / 3);}
}