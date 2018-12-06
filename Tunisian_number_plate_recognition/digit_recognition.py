# importing required libraries of opencv
import cv2
import numpy as np
import matplotlib.pyplot as plt
import csv
import os
import scipy.ndimage
from sklearn.model_selection import  train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.externals import joblib
from resize import resize_carre
from segmentation import crop

# importing library for plotting
from matplotlib import pyplot as plt

# list that will contains all digits
caracrter_list_image =list()

# reads an input image
img = cv2.imread('1.png')
img = crop(img)

BLACK = [0,0,0]
img= cv2.copyMakeBorder(img,3,3,3,3,cv2.BORDER_CONSTANT,value=BLACK)
# change to gray
gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)

nb = np.array(gray)

# seuillage
nb[nb > 120] = 255
nb[nb < 120] = 0

# compute the sommation
x_sum = cv2.reduce(nb, 0, cv2.REDUCE_SUM, dtype=cv2.CV_32S)
y_sum = cv2.reduce(nb, 1, cv2.REDUCE_SUM, dtype=cv2.CV_32S)

# rotate the vector x_sum
x_sum = x_sum.transpose()

# get height and weight
x = gray.shape[1]
y = gray.shape[0]

# division the result by height and weight
x_sum = x_sum/y
y_sum = y_sum/x

# x_arr and y_arr are two vector with lenght weight and height to plot histogram projection properly
x_arr = np.arange(x)
y_arr = np.arange(y)

# convert x_sum to numpy array
z = np.array(x_sum)

# convert y_arr to numpy array
w = np.array(y_sum)

# convert to zero small details
z[z <15] = 0
z[z >15] = 1

# convert to zero small details and 1 for needed details
w[w <20] = 0
w[w >20] = 1

# vertical segmentation
test = z.transpose() * nb

# horizontal segmentation
test = w * test

# plot histogram projection result using pyplot
"""
horizontal = plt.plot(w,y_arr)
vertical = plt.plot(x_arr,z)
plt.show(horizontal)
plt.show(horizontal)
plt.show()"""

f = 0
ff = z[0]
t1 = list()
t2 = list()
for i in range(z.size):
        if(z[i] != ff):
            f += 1
            ff = z[i]
            t1.append(i)
rect_h = np.array(t1)

f = 0
ff = w[0]
for i in range(w.size):
        if(w[i] != ff):
            f += 1
            ff = w[i]
            t2.append(i)
rect_v = np.array(t2)

# take the appropriate height
rectv = []
rectv.append(rect_v[0])
rectv.append(rect_v[1])
max = int(rect_v[1]) - int(rect_v[0])
for i in range(len(rect_v) - 1):
    diff2 = int(rect_v[i+1]) - int(rect_v[i])

    if(diff2 > max):
        rectv[0] = rect_v[i]
        rectv[1] = rect_v[i+1]
        max = diff2
# extract caracter
for i in range(len(rect_h) - 1):
    # eliminate slice that can't be a digit, a digit must have width bigger then 8
    diff1 = int(rect_h[i+1]) - int(rect_h[i])

    if((diff1 > 5) and (z[rect_h[i]] == 1)):
        # cutting nb (image) and adding each slice to the list caracrter_list_image
        caracrter_list_image.append(nb[int(rectv[0]):int(rectv[1]),rect_h[i]:rect_h[i+1]])

        # draw rectangle on digits
        cv2.rectangle(img,(rect_h[i],rectv[0]),(rect_h[i+1],rectv[1]),(0,255,0),1)


image = plt.imshow(img)
plt.show(image)


#**********************************************************************************************************************
#********************************************Prediction with MLP calssifier********************************************
mlp = joblib.load('mlp_model.pkl')
prediction = []
scores = []
for i in range(len(caracrter_list_image)):
    x = caracrter_list_image[i]
    x = resize_carre(x)
    resized = cv2.resize(x, (20,20), interpolation = cv2.INTER_AREA)
    resized = np.array(resized).ravel()
    test = []
    test.append(resized)
    resultat = mlp.predict(test)
    prediction.append(resultat[0])
print(prediction)
