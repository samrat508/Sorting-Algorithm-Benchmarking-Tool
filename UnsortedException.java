/**
 * File: UnsortedException.java
 * Author: Samrat Shakya
 * Course: CMSC451
 * Assignment: PROJECT 1
 * Date: 2/3/26
 *
 * Description:
 * Custom exception thrown when a sorting algorithm fails to
 * correctly sort an array. Used for validation during
 * benchmarking and testing.
 */

public class UnsortedException extends Exception {
    public UnsortedException(String message) {
        super(message);
    }
}