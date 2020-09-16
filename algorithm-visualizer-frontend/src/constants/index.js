export const BASE_URL = 'http://localhost:8080/web-algo';

export const ALGORITHMS = [
    {
        name: "Bubble Sort",
        imageURL: "./public/images/bubblesort.png"
    },
    {
        name: "Insertion Sort",
        imageURL: "./public/images/insertionsort.png"
    },
    {
        name: "Merge Sort",
        imageURL: "./public/images/mergesort.png"
    },
    {
        name: "Selection Sort",
        imageURL: "./public/images/selectionsort.png"
    },
    {
        name: "Quick Sort",
        imageURL: "./public/images/quicksort.png"
    }
];

export const authorizationLink = "http://localhost:8080/web-algo/oauth2/authorize/";

export const redirectUri = "http://localhost:3000/oauth2/redirect";