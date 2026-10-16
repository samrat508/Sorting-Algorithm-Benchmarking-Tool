# Sorting-Algorithm-Benchmarking-Tool
A Java program that compares Heap Sort and Radix Sort. It measures how many operations each algorithm performs and how long each takes on arrays of increasing size, then shows the averages in a table.

Course project for CMSC 451 (Design and Analysis of Computer Algorithms), University of Maryland Global Campus, Spring 2026.

What it does
Runs both algorithms on randomly generated integer arrays at 12 sizes, from 10,000 to 120,000 elements.
Runs each size 40 times. For each run, both algorithms get the same input data (a fixed seed per size and run), so the comparison is fair.
Warms up the JVM before timing, so early runs don't skew the results.
Records an operation count and an execution time for every run, and writes them to HeapSortData.txt and RadixSortData.txt.
Reads a data file you choose and shows averages and coefficients of variation in a Swing table.
Results on my machine

Averages over 40 runs per size:

Array size	Heap Sort	Radix Sort	Heap / Radix time
10,000	0.70 ms	0.36 ms	1.9x
60,000	4.7 ms	2.0 ms	2.4x
120,000	10.1 ms	3.9 ms	2.6x

Radix Sort was faster at every size, and the gap widened as the arrays grew. Its operation count grew in a straight line with array size (18 operations per element in these runs), while Heap Sort's count grew faster than linearly. Timings depend on the machine, so treat the ratios as more meaningful than the exact milliseconds.

Project files
File	Purpose
AbstractSort.java	Base class that tracks operation count and time
HeapSort.java	Heap Sort implementation
RadixSort.java	Radix Sort implementation
UnsortedException.java	Thrown if a result isn't sorted
Benchmark.java	Runs the experiment and writes the data files
Report.java	Swing table of the results
Build and run

Requires a JDK.

cd src
javac *.java
java Benchmark    # writes HeapSortData.txt and RadixSortData.txt
java Report       # choose a data file to view the summary table
Notes
The two sorting algorithms are adapted from standard reference implementations, which are cited in the file headers. The operation counting, benchmarking, and report are my own additions.

Author
Samrat Shakya
