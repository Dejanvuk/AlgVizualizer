import { setArray, toSwap, addCurrentIndex, resetCurentIndex, addToSorted, resetSwap } from '../redux/actions';

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export default async function mergeSort(n, dispatch, pauseTime, func) {
    let arr = [...n];
    sort(arr,0, arr.length - 1, dispatch, pauseTime, func);
}

async function sort(arr, l, r, dispatch, pauseTime, func) {
    if (l < r) {
        let q = Math.floor((l + r) / 2);
        await sort(arr, l, q, dispatch, pauseTime, func);
        await sort(arr, q + 1, r, dispatch, pauseTime, func);
        await merge(arr, l, q, r, dispatch, pauseTime, func);
        for(let i = l ; i < r; i++) {
            if(func()) return;
            dispatch(addToSorted(i));
            await sleep(pauseTime / 3);
        }
    }
}

async function merge(arr, l, q, r, dispatch, pauseTime, func) {
    let i, j, k;
    let n1 = q - l + 1;
    let n2 = r - q;

    let L = [];
    let R = [];

    for (i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (j = 0; j < n2; j++)
        R[j] = arr[q + 1 + j];

    L[i] = Number.MAX_SAFE_INTEGER;
    R[j] = Number.MAX_SAFE_INTEGER;

    i = 0; j = 0; k = l;
    while (k <= r) {
        if(func()) return;
        dispatch(resetCurentIndex());
        dispatch(addCurrentIndex(k));
        await sleep(pauseTime / 3);
        if (L[i] <= R[j]) {
            dispatch(resetSwap());
            dispatch(toSwap(j,i));
            arr[k] = L[i++];
            await dispatch(setArray(arr));
            await sleep(pauseTime / 3);
        }
        else if (R[j] < L[i]) {
            dispatch(resetSwap());
            dispatch(toSwap(j,i));
            arr[k] = R[j++];
            await dispatch(setArray(arr));
            await sleep(pauseTime / 3);
        }
        k++;
    }
}