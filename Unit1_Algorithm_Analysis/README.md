# Project Title

Sorting Complexity Visualizer

## Description

This project compares the theoretical time complexity of Merge Sort and Quick Sort for input sizes n = 10, 100, and 1000. A line chart is used to visualize the O(n log n) growth of Merge Sort and the average-case O(n log n) growth of Quick Sort.

## Algorithm

1. Take the input sizes 10, 100, and 1000.
2. Calculate n log₂(n) for each input size.
3. Use these values as estimated operation counts for Merge Sort and average-case Quick Sort.
4. Display the calculated values.
5. Generate a line chart comparing the two sorting algorithms.
6. Save the chart as `Visualization.png`.

### Pseudocode

START

Set n_values = [10, 100, 1000]

FOR each n in n_values:
Calculate value = n × log₂(n)
Set Merge Sort value = value
Set Quick Sort value = value
END FOR

Display the complexity values

Create a line chart for Merge Sort and Quick Sort

Add title, axis labels, legend, and grid

Save chart as Visualization.png

END

## Prompt Used

Create a clean academic line chart titled "Sorting Complexity: Merge Sort vs Quick Sort".

X-axis: Input Size (n) with values 10, 100, and 1000.

Y-axis: Estimated Operations.

Plot two lines:

1. Merge Sort — O(n log n)
   Values: 33.22, 664.39, 9965.78

2. Quick Sort — Average Case O(n log n)
   Values: 33.22, 664.39, 9965.78

Use different marker styles for the two algorithms, even though their theoretical values overlap.

Include chart title, X-axis label, Y-axis label, legend, grid, clear data points, and professional academic appearance.

Add a small note:
"Quick Sort shown for average-case O(n log n)."

Do not invent benchmark timings. Use only the values provided above.

The diagram should be suitable for a Data Structures and Algorithms project submission.

## Output

The program displays the theoretical operation values for Merge Sort and Quick Sort for n = 10, 100, and 1000. It also automatically generates a line chart and saves it as `Visualization.png`.

Merge Sort: O(n log n)

Quick Sort: O(n log n) average case

## Learning Outcome

* Understood the concept of asymptotic time complexity.
* Compared Merge Sort and Quick Sort.
* Learned about O(n log n) complexity.
* Learned how to visualize algorithmic growth using a line chart.
* Practiced creating and documenting an algorithm project for GitHub.