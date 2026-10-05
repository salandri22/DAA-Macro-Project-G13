# Project 13 – 0/1 Knapsack using Branch and Bound

## Description
Solves the 0/1 Knapsack problem with Branch and Bound (DFS). Each node stores profit, weight and an
upper bound; branches that cannot beat the best known profit are pruned. The state-space tree is
visualized through prompt engineering and matplotlib.

**Input:** W = 10, items (profit, weight) = (40,4), (42,7), (25,5), (12,3)

## Algorithm
```
sort items by profit/weight ratio (descending)
maxProfit = 0

function bound(level, profit, weight):
    if weight > W: return 0
    b = profit; totW = weight; j = level + 1
    while j < n and totW + w[j] <= W:
        totW += w[j]; b += p[j]; j++
    if j < n: b += (W - totW) * p[j] / w[j]     # fractional part
    return b

function explore(level, profit, weight):
    if weight > W: return                        # infeasible
    if profit > maxProfit: maxProfit = profit
    if level == n-1: return
    if bound(level, profit, weight) <= maxProfit: return   # PRUNE
    explore(level+1, profit + p[level+1], weight + w[level+1])  # include
    explore(level+1, profit, weight)                            # exclude
```


**Result:** maximum profit = **65** (items 40 + 25, weight 9). Only 9 nodes were generated.

## Explanation
- Root bound = 76 (items 1 and 2 not fully fit; fractional part of item 3 is added).
- Include item 2 (42, w=7) → weight 11 > 10 → **infeasible (red)**.
- Exclude item 1 at the root (p=0) → bound 57. DFS has already found profit 65 on the left side, and 57 ≤ 65, so this branch is **pruned (orange)**.
- Path include(40) → exclude(42) → include(25) gives profit 65, w=9: the **optimal node (green)**.
- Remaining branches are pruned or infeasible, so the full 2^4 tree is never built.

## How to Run
`javac Project13_Knapsack_BnB.java` then `java Project13_Knapsack_BnB`

The program prints every node and saves the tree as `Visualization.png` (uses only the standard Java library, no extra installs). Java 11+ can also run it directly with `java Project13_Knapsack_BnB.java`.

## Learning Outcome
- Understood bounding and pruning in Branch and Bound.
- Learned prompt-based visualization.
- Practiced GitHub documentation.
