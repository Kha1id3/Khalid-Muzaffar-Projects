import pandas as pd
from io import StringIO


file_path = 't-shirts.csv'  
data = pd.read_csv(file_path)

#capture the basic information
buffer = StringIO()
data.info(buf=buffer)
basic_info = buffer.getvalue()

#display basic statistics
basic_stats = data.describe(include='all')


with open('task1_basic_info.txt', 'w') as f:
    f.write(basic_info)

basic_stats.to_csv('task1_basic_stats.csv', index=False)

print("Basic Information and Statistics saved to task1_basic_info.txt and task1_basic_stats.csv")
