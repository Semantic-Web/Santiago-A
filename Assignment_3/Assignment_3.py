import pandas as pd
from pandas import DataFrame as df

#Windows dir
#path = "C:\Users\santiago\Desktop\FAU\SemanticWeb\Assigment_1\datagovdatasetsviewmetrics.csv"

#Rasberry Pi dir
path = "/home/pi/Desktop/SemanticWeb/Assigment_1/datagovdatasetsviewmetrics.csv"

print "Data Path: " , path

data = pd.read_csv(path)

print "Grouping by Organization Name, then adding the repeated fields"

group = data.groupby('Organization Name')

groupSum = group.sum()

print"done..."
print "*"*100

#print groupSum
#print "*"*100

Organization_Name = groupSum.T.columns.tolist() #transpose
Views_per_Month = groupSum.values

print "Takes the Views_per_Month array that was in an array of arrays and converts it into a single simple array"
view = []
for i in range(len(Views_per_Month)):
    _data = Views_per_Month[i]
    view.append(_data[0])
    
Views_per_Month = view
print "done..."
print "*"*100
#----
print "Combines Organization_Name and Views_per_Month into a single data_array"
data_array = []

for i in range(len(Views_per_Month)):
    _Views_per_Month = Views_per_Month[i]
    _Organization_Name = Organization_Name[i]
    data_array.append((_Organization_Name,_Views_per_Month))

print "done..."
print "*"*100    

print "Sorting data_array by highest to lower order"

sorted_data_array = sorted(data_array,key=lambda x: x[1],reverse=True)
  
print "done..."
print "*"*100

print "The top 10 Organization"

final_array = []
Num_of_Organization = 10
for i in range(0,Num_of_Organization):
    final_array.append(sorted_data_array[i])

from prettytable import PrettyTable

data_table = PrettyTable(["Organization Name", "Views per Month"])
data_table.align["Organization Name"] = "l" # Left align city names
data_table.padding_width = 1 # One space between column edges and contents (default)

for i in range(len(final_array)):
    data_table.add_row(final_array[i])

print data_table

#####
print 

values = []
labels = []

for (Organization_Name, Views_per_Month) in final_array:
    labels.append(Organization_Name)
    values.append(Views_per_Month)

#Abreviates the data
length = 20
short_labels = []
for i in range(len(labels)):
    _data = labels[i]
    
    txt = ""
    
    if(len(_data) > length):
        for j in range(0,length):
            txt = txt + _data[j]
        txt = txt+"..."
    else:
        txt = _data
    short_labels.append(txt)

import numpy as np
from matplotlib import pyplot as plt

fig = plt.figure()

width = .35
ind = np.arange(len(values))
plt.bar(ind, values)
plt.xticks(ind + width / 2, short_labels)

plt.xticks(rotation='vertical')

plt.subplots_adjust(bottom=.4)

plt.show()
