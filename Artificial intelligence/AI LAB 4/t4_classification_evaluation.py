import pandas as pd

# Load Naive Bayes results
with open('task3_naive_bayes_results.txt', 'r') as f:
    nb_results = f.read()

# Load Decision Tree results
with open('task3_decision_tree_results.txt', 'r') as f:
    dt_results = f.read()

# Prepare the evaluation summary
evaluation_summary = f"""
# Task 4: Classification Evaluation

## Naive Bayes Classifier Results
{nb_results}

## Decision Tree Classifier Results
{dt_results}

### Interpretation
The Naive Bayes classifier generally performed better with normalization, achieving an accuracy of 0.67. Other data processing techniques did not significantly improve the performance. PCA reduced the performance, indicating that it may not be suitable for this specific problem.

The Decision Tree classifier performed exceptionally well with almost all configurations, achieving an accuracy of 0.97 for most settings. PCA reduced the performance to 0.87, indicating the loss of crucial information. The hyperparameters did not have a significant impact on the overall performance, suggesting that the default parameters were already well-tuned for this dataset.

### Conclusion
The Decision Tree classifier appears to be the best choice for this dataset due to its consistently high performance across various data processing techniques and hyperparameters.
"""

# Save the evaluation summary to a file
with open('task4_classification_evaluation.txt', 'w') as f:
    f.write(evaluation_summary)

print("Classification evaluation summary saved to task4_classification_evaluation.txt")
