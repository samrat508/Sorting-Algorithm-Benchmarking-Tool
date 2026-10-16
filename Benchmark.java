/**
 * File: Benchmark.java
 * Author: Samrat Shakya
 * Course: CMSC451
 * Assignment: PROJECT 1
 * Date: 2/3/26
 *
 * Description:
 * Benchmarks Heap Sort and Radix Sort on randomly generated
 * integer arrays of varying sizes. For each size, multiple
 * runs are performed using identical input data, and the
 * operation counts and execution times are written to output
 * files for later analysis.
 */


import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark {

    public static void main(String[] args) {

        int[] sizes = {10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 110000, 120000};
        int numRuns = 40;
        Random random = new Random();

        // === JVM Warm-up (very important for accurate nanoTime measurements) ===
        System.out.println("Performing JVM warm-up...");
        warmUp(random);

        // === Benchmark both algorithms ===
        try (
                PrintWriter heapWriter = new PrintWriter("HeapSortData.txt");
                PrintWriter radixWriter = new PrintWriter("RadixSortData.txt")
        ) {
            for (int size : sizes) {
                System.out.println("Benchmarking size = " + size);

                StringBuilder heapLine = new StringBuilder(String.valueOf(size));
                StringBuilder radixLine = new StringBuilder(String.valueOf(size));

                for (int run = 0; run < numRuns; run++) {
                    // Use a fixed seed for this exact (size, run) combination
                    // so both algorithms get IDENTICAL data
                    long seed = 42L + (long) size * 10000 + run;
                    random.setSeed(seed);

                    int[] original = generateArray(size, random);

                    // HeapSort run
                    int[] dataHeap = original.clone();
                    HeapSort heap = new HeapSort();
                    heap.sort(dataHeap);
                    heapLine.append(" ").append(heap.getCount())
                            .append(" ").append(heap.getTime());

                    // RadixSort run
                    int[] dataRadix = original.clone();
                    RadixSort radix = new RadixSort();
                    radix.sort(dataRadix);
                    radixLine.append(" ").append(radix.getCount())
                            .append(" ").append(radix.getTime());
                }

                heapWriter.println(heapLine);
                radixWriter.println(radixLine);
            }

            System.out.println("Benchmark complete. Files written:");
            System.out.println("  HeapSortData.txt");
            System.out.println("  RadixSortData.txt");

        } catch (IOException | UnsortedException e) {
            e.printStackTrace();
        }
    }

    private static void warmUp(Random random) {
        // Run many small sorts to warm up JIT compiler
        for (int i = 0; i < 150; i++) {  // adjust if needed
            int[] warm = generateArray(5000 + i % 3000, random);
            try {
                new HeapSort().sort(warm.clone());
                new RadixSort().sort(warm.clone());
            } catch (UnsortedException ignored) {
            }
        }
    }

    private static int[] generateArray(int size, Random random) {
        int[] arr = new int[size];
        // Non-negative integers (required for standard Radix Sort)
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1_000_000_000); // 0 to ~1 billion
            // or: random.nextInt(Integer.MAX_VALUE) if you want full 32-bit range
        }
        return arr;
    }
}