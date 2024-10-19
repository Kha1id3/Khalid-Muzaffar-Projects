import pandas as pd
import matplotlib.pyplot as plt
from sklearn.metrics import classification_report

# Function to extract metrics from classification report
def extract_metrics(report):
    lines = report.split('\n')
    metrics = {}
    for line in lines[2:-5]:
        row = line.split()
        if len(row) >= 5:
            metrics[row[0]] = [float(x) for x in row[1:5]]
    return metrics

# Load Naive Bayes results
with open('task3_naive_bayes_results.txt', 'r') as f:
    nb_results = f.read().strip().split("\n\n")

# Load Decision Tree results
with open('task3_decision_tree_results.txt', 'r') as f:
    dt_results = f.read().strip().split("\n\n")

# Extract metrics for plotting
nb_metrics_list = [extract_metrics(result.split("\n", 1)[1]) for result in nb_results]
dt_metrics_list = [extract_metrics(result.split("\n", 1)[1]) for result in dt_results]

# Define classes and metrics
class_labels = ['high', 'low', 'medium']
metrics_names = ['precision', 'recall', 'f1-score']

# Simplified Plotting
def plot_simple_metrics(metrics_list, classifier_name):
    for metric_idx, metric in enumerate(metrics_names):
        plt.figure(figsize=(10, 6))

        # Average the metrics for each class
        avg_values = {label: [] for label in class_labels}
        for metrics in metrics_list:
            for label in class_labels:
                if label in metrics:
                    avg_values[label].append(metrics[label][metric_idx])
                else:
                    avg_values[label].append(0)

        avg_values = {label: sum(values) / len(values) for label, values in avg_values.items()}

        plt.bar(avg_values.keys(), avg_values.values(), color='blue' if classifier_name == 'Naive Bayes' else 'red', alpha=0.6)

        plt.title(f'Average {metric.capitalize()} Comparison ({classifier_name})')
        plt.xlabel('Class')
        plt.ylabel(metric.capitalize())
        plt.ylim(0, 1)
        plt.grid(True)
        plt.savefig(f'avg_{classifier_name.lower()}_{metric}.png')
        plt.show()

# Plot average metrics for Naive Bayes
plot_simple_metrics(nb_metrics_list, 'Naive Bayes')

# Plot average metrics for Decision Tree
plot_simple_metrics(dt_metrics_list, 'Decision Tree')
