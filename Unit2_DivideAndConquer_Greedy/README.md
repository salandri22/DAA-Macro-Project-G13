# Project Title

Optimal Merge Pattern using Greedy Algorithm

## Description

This project implements the Optimal Merge Pattern using a greedy algorithm. It finds the minimum total cost required to merge files of sizes 10, 20, 30, and 40. The optimal merge process is visualized using a binary tree.

## Algorithm

1. Insert all file sizes into a min-heap.
2. Select the two smallest files.
3. Merge the two files and calculate their combined size.
4. Add the merged size to the total merge cost.
5. Insert the merged file back into the min-heap.
6. Repeat until only one file remains.
7. Display the optimal merge tree and total merge cost.

### Pseudocode

START

Input files = [10, 20, 30, 40]

Create a min-heap using the file sizes.

total_cost = 0

WHILE more than one file exists:
    first = remove smallest file
    second = remove second smallest file
    merged = first + second
    total_cost = total_cost + merged
    insert merged back into the min-heap

Display total_cost

END

## Prompt Used

"Draw a binary tree showing the optimal merge pattern for files of sizes 10, 20, 30, and 40. The tree should clearly show:
1. 10 + 20 = 30
2. 30 + 30 = 60
3. 40 + 60 = 100
Show file sizes and merged values clearly in nodes. Display total merge cost as 190. Create a clean academic diagram suitable for a Data Structures and Algorithms project presentation."

## Output

The optimal merge steps are:

- 10 + 20 = 30
- 30 + 30 = 60
- 40 + 60 = 100

Total Merge Cost = 190

The generated visualization is attached as `Visualization.png`.

## Learning Outcome

- Understood the Optimal Merge Pattern.
- Learned how greedy algorithms select the two smallest files.
- Understood the use of a min-heap.
- Learned how to visualize the optimal merge process using a binary tree.
- Practiced prompt-based visualization and GitHub documentation.