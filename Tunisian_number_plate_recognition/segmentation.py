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

# importing library for plotting
from matplotlib import pyplot as plt


def crop(img):


        BLACK = [0,0,0]
        img= cv2.copyMakeBorder(img,3,3,3,3,cv2.BORDER_CONSTANT,value=BLACK)
        # change to gray
        gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)

        nb = np.array(gray)

        # seuillage
        nb[nb > 120] = 255
        nb[nb < 120] = 0

        # compute the sommation
        y_sum1 = cv2.reduce(nb, 1, cv2.REDUCE_SUM, dtype=cv2.CV_32S)


        # get height and weight
        x1 = gray.shape[1]
        y1 = gray.shape[0]

        # division the result by height and weight
        y_sum1 = y_sum1/x1

        # x_arr and y_arr are two vector with lenght weight and height to plot histogram projection properly
        y_arr1 = np.arange(y1)


        # convert y_arr to numpy array
        w1 = np.array(y_sum1)

        # convert to zero small details and 1 for needed details
        w1[w1 <15] = 0
        w1[w1 >15] = 1


        # horizontal segmentation
        test1 = w1 * nb


        t0 = list()
        f = 0
        ff = w1[0]
        for i in range(w1.size):
                if(w1[i] != ff):
                    f += 1
                    ff = w1[i]
                    t0.append(i)
        rect_v1 = np.array(t0)
        ################################################################################
        ############ take the appropriate height
        rectv1 = []
        rectv1.append(rect_v1[0])
        rectv1.append(rect_v1[1])
        max = int(rect_v1[1]) - int(rect_v1[0])
        for i in range(len(rect_v1) - 1):
            diff0 = int(rect_v1[i+1]) - int(rect_v1[i])

            if(diff0 > max):
                rectv1[0] = rect_v1[i]
                rectv1[1] = rect_v1[i+1]
                max = diff0
        cropped = img[rectv1[0]:rectv1[1], :]
        return cropped
