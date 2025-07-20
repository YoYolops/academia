import matplotlib.pyplot as plt
import numpy as np
import os

# Create the plots directory if it doesn't exist
os.makedirs("plots", exist_ok=True)

def extract_series(data):
    x = [t[0] for t in data]
    collisions = [t[1] for t in data]
    false_positives = [t[2] for t in data]
    occupation = [t[3] for t in data]
    return x, collisions, false_positives, occupation

# Extract data
x1, col1, fp1, occ1 = extract_series(consistency_result_md5_sha2)
x2, col2, fp2, occ2 = extract_series(consistency_result_djb2_sdbm)
x3, col3, fp3, occ3 = extract_series(consistency_result_xxhash)

# --- Plot 1: Filter Occupation ---
plt.figure(figsize=(8, 5))
plt.plot(x1[:40], occ1[:40], label="MD5+SHA2", color="blue")
plt.plot(x2[:40], occ2[:40], label="DJB2+SDBM", color="green")
plt.plot(x3[:40], occ3[:40], label="xxHash", color="red")
plt.title("Filter Occupation Over Time")
plt.xlabel("Number of Inserted Items")
plt.ylabel("Filter Occupation")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig("plots/occupation.png")
plt.close()

# --- Plot 2: False Positives ---
plt.figure(figsize=(8, 5))
plt.plot(x1[:40], fp1[:40], label="MD5+SHA2", color="blue")
plt.plot(x2[:40], fp2[:40], label="DJB2+SDBM", color="green")
plt.plot(x3[:40], fp3[:40], label="xxHash", color="red")
plt.title("False Positives Over Time")
plt.xlabel("Number of Inserted Items")
plt.ylabel("Number of False Positives")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig("plots/false_positives.png")
plt.close()

# --- Plot 3: Collisions ---
plt.figure(figsize=(8, 5))
plt.plot(x1, col1, label="MD5+SHA2", color="blue")
plt.plot(x2, col2, label="DJB2+SDBM", color="green")
plt.plot(x3, col3, label="xxHash", color="red")
plt.title("Collisions Over Time")
plt.xlabel("Number of Inserted Items")
plt.ylabel("Number of Collisions")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig("plots/collisions.png")
plt.close()

# Grouped bar plot: Total insertion time (log y-scale, fixed ticks)
def plot_grouped_insertion_time():
    data_sets = [
        ("MD5+SHA2 k=8", time_result_md5_sha2_k1),
        ("MD5+SHA2 k=48", time_result_md5_sha2_k2),
        ("DJB2+SDBM k=8", time_result_djb2_sdbm_k1),
        ("DJB2+SDBM k=48", time_result_djb2_sdbm_k2),
        ("xxHash k=8", time_result_xxhash_k1),
        ("xxHash k=48", time_result_xxhash_k2),
    ]

    group_labels = [100, 1000, 5000]
    bar_width = 0.12
    x = np.arange(len(group_labels))
    
    plt.figure(figsize=(10, 6))
    
    for i, (label, data) in enumerate(data_sets):
        insertion_times = [d[1] for d in data]
        plt.bar(x + i * bar_width, insertion_times, bar_width, label=label)

    plt.yscale('log')  # <- Logarithmic scale for better visual contrast
    plt.xlabel("Number of Inserted Elements")
    plt.ylabel("Total Insertion Time (ns, log scale)")
    plt.title("Total Insertion Time by Strategy and Element Count")
    plt.xticks(x + bar_width * 2.5, group_labels)
    plt.legend()
    plt.grid(True, axis='y')
    plt.tight_layout()
    plt.savefig("plots/grouped_insertion_time.png")
    plt.close()

# Line plot helper (force x-ticks to 100, 1000, 5000)
def line_plot(title, ylabel, filename, datasets):
    plt.figure(figsize=(8, 5))
    for label, data in datasets:
        x = [t[0] for t in data]
        y = [t[3] for t in data] if "search" in filename else [t[2] for t in data]
        plt.plot(x, y, marker='o', label=label)

    plt.title(title)
    plt.xlabel("Number of Inserted Items")
    plt.ylabel(ylabel)
    plt.xticks([100, 1000, 5000])  # <- Explicit tick locations
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(f"plots/{filename}.png")
    plt.close()

# Search time comparisons
def plot_search_time_lines():
    line_plot("Search Time - All k=8", "Search Time for 1000 Searches (ns)", "search_time_k1", [
        ("MD5+SHA2 k=8", time_result_md5_sha2_k1),
        ("DJB2+SDBM k=8", time_result_djb2_sdbm_k1),
        ("xxHash k=8", time_result_xxhash_k1),
    ])

    line_plot("Search Time - All k=48", "Search Time for 1000 Searches (ns)", "search_time_k2", [
        ("MD5+SHA2 k=48", time_result_md5_sha2_k2),
        ("DJB2+SDBM k=48", time_result_djb2_sdbm_k2),
        ("xxHash k=48", time_result_xxhash_k2),
    ])

    line_plot("Search Time - xxHash k1 vs k2", "Search Time for 1000 Searches (ns)", "search_time_xxhash", [
        ("xxHash k=8", time_result_xxhash_k1),
        ("xxHash k=48", time_result_xxhash_k2),
    ])

    line_plot("Search Time - DJB2+SDBM k1 vs k2", "Search Time for 1000 Searches (ns)", "search_time_djb2", [
        ("DJB2+SDBM k=8", time_result_djb2_sdbm_k1),
        ("DJB2+SDBM k=48", time_result_djb2_sdbm_k2),
    ])

    line_plot("Search Time - MD5+SHA2 k1 vs k2", "Search Time for 1000 Searches (ns)", "search_time_sha", [
        ("MD5+SHA2 k=8", time_result_md5_sha2_k1),
        ("MD5+SHA2 k=48", time_result_md5_sha2_k2),
    ])

# Avg. insertion time comparisons
def plot_avg_insertion_lines():
    line_plot("Average Insertion Time - All k=8", "Avg. Insertion Time (ns)", "avg_insert_time_k1", [
        ("MD5+SHA2 k=8", time_result_md5_sha2_k1),
        ("DJB2+SDBM k=8", time_result_djb2_sdbm_k1),
        ("xxHash k=8", time_result_xxhash_k1),
    ])

    line_plot("Average Insertion Time - All k=48", "Avg. Insertion Time (ns)", "avg_insert_time_k2", [
        ("MD5+SHA2 k=48", time_result_md5_sha2_k2),
        ("DJB2+SDBM k=48", time_result_djb2_sdbm_k2),
        ("xxHash k=48", time_result_xxhash_k2),
    ])

    line_plot("Avg. Insertion Time - xxHash k1 vs k2", "Avg. Insertion Time (ns)", "avg_insert_xxhash", [
        ("xxHash k=8", time_result_xxhash_k1),
        ("xxHash k=48", time_result_xxhash_k2),
    ])

    line_plot("Avg. Insertion Time - DJB2+SDBM k1 vs k2", "Avg. Insertion Time (ns)", "avg_insert_djb2", [
        ("DJB2+SDBM k=8", time_result_djb2_sdbm_k1),
        ("DJB2+SDBM k=48", time_result_djb2_sdbm_k2),
    ])

    line_plot("Avg. Insertion Time - MD5+SHA2 k1 vs k2", "Avg. Insertion Time (ns)", "avg_insert_sha", [
        ("MD5+SHA2 k=8", time_result_md5_sha2_k1),
        ("MD5+SHA2 k=48", time_result_md5_sha2_k2),
    ])

# Run all plots
plot_grouped_insertion_time()
plot_search_time_lines()
plot_avg_insertion_lines()