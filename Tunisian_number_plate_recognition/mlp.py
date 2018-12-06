from sklearn.neural_network import MLPClassifier
from sklearn.externals import joblib
import os
import cv2
import numpy as np
import csv

features_list = []
features_label = []

# Loadind the dataset (feautures and labels)

for digit in range(0,10):
    label = digit
    training_directory = 'traning_data_set/' + str(label) + '/'
    for filename in os.listdir(training_directory):
        if (filename.endswith('.jpg')):
            training_digit_image = cv2.imread(training_directory + filename)
            gray = cv2.cvtColor(training_digit_image,cv2.COLOR_BGR2GRAY)
            df = np.array(gray).ravel()
            features_list.append(df)
            features_label.append(label)

label = "tunisie"
training_directory = 'traning_data_set/' + str(label) + '/'
for filename in os.listdir(training_directory):
    if (filename.endswith('.jpg')):
        training_digit_image = cv2.imread(training_directory + filename)
        gray = cv2.cvtColor(training_digit_image,cv2.COLOR_BGR2GRAY)
        df = np.array(gray).ravel()

        features_list.append(df)
        features_label.append(label)


X_train = np.array(features_list)
y_train = np.array(features_label)

# Multi layer perceptron classifier declaration
clf = MLPClassifier(solver='adam', alpha=1e-5,hidden_layer_sizes=3000, random_state=1,max_iter=200000)

# Training
clf.fit(X_train,y_train)

# Saving the model
joblib.dump(clf, 'mlp_model.pkl')
