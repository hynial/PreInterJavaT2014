package com.lainyh.playground.basenote;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BinarySearch {

    private int[] arr = new int[]{2, 1, 3, 7, 4, 5, 6, 0, 2, 3, 9, 3};

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();

        Arrays.sort(binarySearch.arr);
        System.out.println(Arrays.toString(binarySearch.arr));
        System.out.println(Arrays.toString(IntStream.range(0, binarySearch.arr.length).toArray()));

        for(int i : binarySearch.arr) {
            System.out.println(i + ", index:" + Arrays.binarySearch(binarySearch.arr, i));
        }

        int excludeVal = -2;
        System.out.println(excludeVal + ", index:" + Arrays.binarySearch(binarySearch.arr, excludeVal));
    }
}
