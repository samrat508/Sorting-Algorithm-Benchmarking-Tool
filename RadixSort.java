/**
 * File: RadixSort.java
 * Author: Samrat Shakya
 * Course: CMSC451
 * Assignment: PROJECT 1
 * Date: 2/3/26
 *
 * Description:
 * Implements the Least Significant Digit (LSD) variant of Radix Sort with
 * instrumentation for counting critical operations. The implementation is
 * adapted from the GeeksforGeeks Radix Sort reference (see citation below).
 * During sorting, the algorithm counts each digit extraction performed while
 * processing array elements in both the counting and placement phases of the
 * digit-wise counting sort. The algorithm iteratively sorts the array by
 * increasing powers of 10 until all digit positions have been processed.
 *
 * Citation:
 * Radix Sort (LSD) implementation adapted from GeeksforGeeks.
 * Source: https://www.geeksforgeeks.org/radix-sort/
 * Accessed: February 2025
 */


import java.util.Arrays;

public class RadixSort extends AbstractSort {

    @Override
    public void sort(int[] array) throws UnsortedException {
        startSort();

        int n = array.length;

        // Find the maximum number to know number of digits
        int max = getMax(array, n);

        // Do counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(array, n, exp);
        }

        endSort();

        if (!isSorted(array)) {
            throw new UnsortedException("RadixSort failed to sort the array correctly.");
        }
    }

    // A utility function to get maximum value in arr[]
    private int getMax(int[] arr, int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > mx) {
                mx = arr[i];
            }
        }
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp (10^i).
    private void countSort(int[] arr, int n, int exp) {
        int[] output = new int[n];           // output array
        int[] count = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (int i = 0; i < n; i++) {
            incrementCount();   // ← count each digit extraction
            count[(arr[i] / exp) % 10]++;
        }

        // Change count[i] so that count[i] contains actual
        // position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            incrementCount();   // ← count each digit extraction (used for placement)
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // Copy the output array to arr[]
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

}