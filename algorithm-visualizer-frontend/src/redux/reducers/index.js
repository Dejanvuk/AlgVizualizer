import { combineReducers } from "redux";
import array from './array';
import index from './currentIndex';
import swap from './swap';
import sortedArray from './sortedArray';
import user from './user';

export default combineReducers({ user, array, index, swap, sortedArray });
