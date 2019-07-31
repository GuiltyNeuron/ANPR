"""
Author : Achraf Khazri AI Research Engineer
Project : Automatic licence plate detection and recognition

"""

from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import confusion_matrix
from sklearn.externals import joblib
from sklearn import svm
import numpy as np
import os
import cv2


def mlp(data, labels, output_file_path):

    # Split data into test and train data
    x_train, x_test, y_train, y_test = train_test_split(data, labels, test_size=0.33, random_state=42)

    # Multi layer perceptron class instance
    clf = MLPClassifier(solver='adam', alpha=1e-5, hidden_layer_sizes=3000, random_state=1, max_iter=200000)

    # Training
    clf.fit(x_train, y_train)

    # Testing
    y_pred = clf.predict(x_test)

    # Confusion matrix
    cm = confusion_matrix(y_test, y_pred)

    # Accuracy
    accuracy = clf.score(x_test, y_test)

    # Saving the model
    joblib.dump(clf, output_file_path)

    print("\n-- Multi Layer Perceptron --")
    print("Training completed")
    print("Accuracy : " + str(accuracy))
    print("Confusion Matrix :")
    print(cm)


def knn(data, labels, output_file_path):

    # Split data into test and train data
    x_train, x_test, y_train, y_test = train_test_split(data, labels, test_size=0.33, random_state=42)

    # KNN class instance
    clf = KNeighborsClassifier(n_neighbors=3)

    # Training
    clf.fit(x_train, y_train)

    # Testing
    y_pred = clf.predict(x_test)

    # Confusion matrix
    cm = confusion_matrix(y_test, y_pred)

    # Accuracy
    accuracy = clf.score(x_test, y_test)

    # Saving the model
    joblib.dump(clf, output_file_path)

    print("\n-- K Nearest Neighbors --")
    print("Training completed")
    print("Accuracy : " + str(accuracy))
    print("Confusion Matrix :")
    print(cm)


def svm(data, labels, output_file_path):

    # Split data into test and train data
    x_train, x_test, y_train, y_test = train_test_split(data, labels, test_size=0.33, random_state=42)

    # SVM class instance
    clf = svm.SVC(kernel='linear', C=1, gamma=1)

    # Training
    clf.fit(x_train, y_train)

    # Testing
    y_pred = clf.predict(x_test)

    # Confusion matrix
    cm = confusion_matrix(y_test, y_pred)

    # Accuracy
    accuracy = clf.score(x_test, y_test)

    # Saving the model
    joblib.dump(clf, output_file_path)

    print("Training completed")
    print("Training Accuracy : " + str(accuracy))
    print("Confusion Matrix :")
    print(cm)

