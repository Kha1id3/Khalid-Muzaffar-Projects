import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import classification_report

#Loading the training and validation sets
X_train = pd.read_csv('X_train.csv')
X_val = pd.read_csv('X_val.csv')
y_train = pd.read_csv('y_train.csv').values.ravel()
y_val = pd.read_csv('y_val.csv').values.ravel()

#Loading processed data
X_train_normalized = pd.read_csv('X_train_normalized.csv')
X_val_normalized = pd.read_csv('X_val_normalized.csv')
X_train_standardized = pd.read_csv('X_train_standardized.csv')
X_val_standardized = pd.read_csv('X_val_standardized.csv')
X_train_discretized = pd.read_csv('X_train_discretized.csv')
X_val_discretized = pd.read_csv('X_val_discretized.csv')
X_train_pca = pd.read_csv('X_train_pca.csv')
X_val_pca = pd.read_csv('X_val_pca.csv')

# Naive Bayes Classifier with Different Hyperparameters
nb_params = [1e-9, 1e-8, 1e-7]
nb_results = []

for var_smoothing in nb_params:
    nb = GaussianNB(var_smoothing=var_smoothing)
    
    # Without data processing
    nb.fit(X_train, y_train)
    y_pred = nb.predict(X_val)
    nb_results.append((f"Naive Bayes without data processing (var_smoothing={var_smoothing})", classification_report(y_val, y_pred)))
    
    # With normalization
    nb.fit(X_train_normalized, y_train)
    y_pred_normalized = nb.predict(X_val_normalized)
    nb_results.append((f"Naive Bayes with normalization (var_smoothing={var_smoothing})", classification_report(y_val, y_pred_normalized)))
    
    # With standardization
    nb.fit(X_train_standardized, y_train)
    y_pred_standardized = nb.predict(X_val_standardized)
    nb_results.append((f"Naive Bayes with standardization (var_smoothing={var_smoothing})", classification_report(y_val, y_pred_standardized)))
    
    # With discretization
    nb.fit(X_train_discretized, y_train)
    y_pred_discretized = nb.predict(X_val_discretized)
    nb_results.append((f"Naive Bayes with discretization (var_smoothing={var_smoothing})", classification_report(y_val, y_pred_discretized)))
    
    # With PCA
    nb.fit(X_train_pca, y_train)
    y_pred_pca = nb.predict(X_val_pca)
    nb_results.append((f"Naive Bayes with PCA (var_smoothing={var_smoothing})", classification_report(y_val, y_pred_pca)))

# Save Naive Bayes results
with open('task3_naive_bayes_results.txt', 'w') as f:
    for result in nb_results:
        f.write(result[0] + "\n" + result[1] + "\n\n")

# Decision Tree Classifier with Different Hyperparameters
dt_params = [
    {'max_depth': 5, 'min_samples_split': 2, 'min_samples_leaf': 1},
    {'max_depth': 10, 'min_samples_split': 5, 'min_samples_leaf': 2},
    {'max_depth': 15, 'min_samples_split': 10, 'min_samples_leaf': 5}
]
dt_results = []

for params in dt_params:
    dt = DecisionTreeClassifier(**params, random_state=42)
    
    # Without data processing
    dt.fit(X_train, y_train)
    y_pred = dt.predict(X_val)
    dt_results.append((f"Decision Tree without data processing (params={params})", classification_report(y_val, y_pred)))
    
    # With normalization
    dt.fit(X_train_normalized, y_train)
    y_pred_normalized = dt.predict(X_val_normalized)
    dt_results.append((f"Decision Tree with normalization (params={params})", classification_report(y_val, y_pred_normalized)))
    
    # With standardization
    dt.fit(X_train_standardized, y_train)
    y_pred_standardized = dt.predict(X_val_standardized)
    dt_results.append((f"Decision Tree with standardization (params={params})", classification_report(y_val, y_pred_standardized)))
    
    # With discretization
    dt.fit(X_train_discretized, y_train)
    y_pred_discretized = dt.predict(X_val_discretized)
    dt_results.append((f"Decision Tree with discretization (params={params})", classification_report(y_val, y_pred_discretized)))
    
    # With PCA
    dt.fit(X_train_pca, y_train)
    y_pred_pca = dt.predict(X_val_pca)
    dt_results.append((f"Decision Tree with PCA (params={params})", classification_report(y_val, y_pred_pca)))

# Save Decision Tree results
with open('task3_decision_tree_results.txt', 'w') as f:
    for result in dt_results:
        f.write(result[0] + "\n" + result[1] + "\n\n")

print("Classification results saved to task3_naive_bayes_results.txt and task3_decision_tree_results.txt")
