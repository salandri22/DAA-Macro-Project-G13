import heapq
import matplotlib.pyplot as plt


def optimal_merge(files):
    heapq.heapify(files)

    total_cost = 0
    steps = []

    while len(files) > 1:
        first = heapq.heappop(files)
        second = heapq.heappop(files)

        merged = first + second
        total_cost += merged

        steps.append((first, second, merged))

        heapq.heappush(files, merged)

    return total_cost, steps


# Input file sizes
files = [10, 20, 30, 40]

# Calculate optimal merge pattern
total_cost, steps = optimal_merge(files.copy())

print("Optimal Merge Pattern")
print("---------------------")

for first, second, merged in steps:
    print(f"{first} + {second} = {merged}")

print("---------------------")
print("Total Merge Cost =", total_cost)


# Create the merge tree visualization
fig, ax = plt.subplots(figsize=(10, 6))

ax.axis("off")

# Tree connections
connections = [
    ((0.50, 0.85), (0.25, 0.65)),
    ((0.50, 0.85), (0.75, 0.65)),
    ((0.75, 0.65), (0.60, 0.40)),
    ((0.75, 0.65), (0.90, 0.40)),
    ((0.60, 0.40), (0.50, 0.15)),
    ((0.60, 0.40), (0.70, 0.15))
]

for start, end in connections:
    ax.plot(
        [start[0], end[0]],
        [start[1], end[1]],
        linewidth=2
    )

# Tree nodes
nodes = [
    (0.50, 0.85, "100"),
    (0.25, 0.65, "40"),
    (0.75, 0.65, "60"),
    (0.60, 0.40, "30"),
    (0.90, 0.40, "30"),
    (0.50, 0.15, "10"),
    (0.70, 0.15, "20")
]

for x, y, label in nodes:
    ax.scatter(x, y, s=1500)
    ax.text(
        x,
        y,
        label,
        ha="center",
        va="center",
        fontsize=12
    )

ax.set_title(
    "Optimal Merge Pattern Tree\n"
    "Files: 10, 20, 30, 40 | Total Cost: 190",
    fontsize=14
)

plt.tight_layout()

# Save visualization
plt.savefig("Visualization.png", dpi=300)

plt.show()