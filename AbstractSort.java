/**
 * File: AbstractSort.java
 * Author: Samrat Shakya
 * Course: CMSC451
 * Assignment: PROJECT 1
 * Date: 2/3/26
 *
 * Description:
 * Abstract base class for sorting algorithms used in the benchmark.
 * Defines common fields and methods for tracking operation counts
 * and execution time, and enforces a standard sort interface.
 */

public abstract class AbstractSort {
    private long count = 0;
    private long startTime;
    private long elapsedTime;

    public abstract void sort(int[] array) throws UnsortedException;

    protected void startSort() {
        count = 0;
        startTime = System.nanoTime();
    }

    protected void endSort() {
        elapsedTime = System.nanoTime() - startTime;
    }

    protected void incrementCount() {
        count++;
    }

    public long getCount() {
        return count;
    }

    public long getTime() {
        return elapsedTime;
    }

    // Helper: swap two elements
    protected void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Helper: check if sorted (ascending)
    protected boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }
}