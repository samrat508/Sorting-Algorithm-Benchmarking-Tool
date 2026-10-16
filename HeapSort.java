/**
 * File: HeapSort.java
 * Author: Samrat Shakya
 * Course: CMSC451
 * Assignment: PROJECT 1
 * Date: 2/3/26
 *
 * Description:
 * Implements the Heap Sort algorithm with instrumentation for counting
 * critical operations. The implementation is adapted from the GeeksforGeeks
 * Heap Sort reference (see citation below). During sorting, the algorithm
 * tracks the number of element-to-element comparisons performed inside the
 * heapify procedure. After building the max heap, elements are extracted
 * one at a time and the heap is re‑heapified until the array is fully sorted.
 *
 * Citation:
 * Heap Sort implementation adapted from GeeksforGeeks.
 * Source: https://www.geeksforgeeks.org/heap-sort/
 * Accessed: February 2026
 */

public class HeapSort extends AbstractSort {

    @Override
    public void sort(int[] array) throws UnsortedException {
        startSort();

        int n = array.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(array, 0, i);

            // Call max heapify on the reduced heap
            heapify(array, i, 0);
        }

        endSort();

        if (!isSorted(array)) {
            throw new UnsortedException("HeapSort failed to sort the array correctly.");
        }
    }

    // To heapify a subtree rooted with node i
    private void heapify(int[] arr, int n, int i) {
        // Initialize largest as root
        int largest = i;
        // left = 2*i + 1
        int l = 2 * i + 1;
        // right = 2*i + 2
        int r = 2 * i + 2;

        // If left child is larger than root — count the comparison
        if (l < n) {
            incrementCount();
            if (arr[l] > arr[largest]) {
                largest = l;
            }
        }

        // If right child is larger than largest so far — count the comparison
        if (r < n) {
            incrementCount();
            if (arr[r] > arr[largest]) {
                largest = r;
            }
        }

        // If largest is not root
        if (largest != i) {
            // Swap using the inherited helper method
            swap(arr, i, largest);

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

}