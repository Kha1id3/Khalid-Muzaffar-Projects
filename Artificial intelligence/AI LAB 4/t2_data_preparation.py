import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MinMaxScaler, StandardScaler, KBinsDiscretizer
from sklearn.decomposition import PCA

#loading data
file_path = 't-shirts.csv'  
data = pd.read_csv(file_path)

#Split the data into features (X) and target (y)
X = data.drop('demand', axis=1)
y = data['demand']

# Convert categorical variables into dummy/indicator variables
X = pd.get_dummies(X)

# Split the dataset into training and validation sets
X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=0.2, random_state=42)

# Save the training and validation sets to csv files
X_train.to_csv('X_train.csv', index=False)
X_val.to_csv('X_val.csv', index=False)
y_train.to_csv('y_train.csv', index=False)
y_val.to_csv('y_val.csv', index=False)

# ----------Apply data processing techniques------------------

# --Normalization--
scaler = MinMaxScaler()
X_train_normalized = scaler.fit_transform(X_train)
X_val_normalized = scaler.transform(X_val)
pd.DataFrame(X_train_normalized).to_csv('X_train_normalized.csv', index=False)
pd.DataFrame(X_val_normalized).to_csv('X_val_normalized.csv', index=False)

# --Standardization--
scaler = StandardScaler()
X_train_standardized = scaler.fit_transform(X_train)
X_val_standardized = scaler.transform(X_val)
pd.DataFrame(X_train_standardized).to_csv('X_train_standardized.csv', index=False)
pd.DataFrame(X_val_standardized).to_csv('X_val_standardized.csv', index=False)

# --Discretization--
discretizer = KBinsDiscretizer(n_bins=10, encode='ordinal', strategy='uniform')
X_train_discretized = discretizer.fit_transform(X_train)
X_val_discretized = discretizer.transform(X_val)
pd.DataFrame(X_train_discretized).to_csv('X_train_discretized.csv', index=False)
pd.DataFrame(X_val_discretized).to_csv('X_val_discretized.csv', index=False)

# --PCA--
pca = PCA(n_components=5)
X_train_pca = pca.fit_transform(X_train)
X_val_pca = pca.transform(X_val)
pd.DataFrame(X_train_pca).to_csv('X_train_pca.csv', index=False)
pd.DataFrame(X_val_pca).to_csv('X_val_pca.csv', index=False)

print("Data processing completed and saved to respective files.")
