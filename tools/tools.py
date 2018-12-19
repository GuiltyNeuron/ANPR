""" Tools.
This contains tools that normalize dataset for training
Links:

Author: Achraf Khazri
Project:
"""

# ------------------------------------------------------------------
#
# THIS FILE HAS BEEN RENAMED 'tools.py', FOR SIMPLICITY.
#
# ------------------------------------------------------------------

import cv2
import numpy as np
import matplotlib.pyplot as plt



def resizeSquare(img,shape): # the input is a numpy array

    h = img.shape[0]
    w = img.shape[1]

    # In case height superior than width
    if(h>w):
        diff = h-w
        if( diff % 2 == 0):
            x1 = np.zeros(shape=(h,diff//2))
            x2 = x1
        else:
            x1 = np.zeros(shape=(h,diff//2))
            x2 = np.zeros(shape=(h,(diff//2)+1))


        squared = np.concatenate((x1, img, x2), axis=1)
        result = cv2.resize(squared, (shape, shape))

    # In case widthsuperior than height
    if(h<w):
        diff = w-h
        if( diff % 2 == 0):
            x1 = np.zeros(shape=(diff//2,w))
            x2 = x1
        else:
            x1 = np.zeros(shape=(diff//2,w))
            x2 = np.zeros(shape=((diff//2)+1,w))
        squared = np.concatenate((x1, img, x2), axis=0)
        result = cv2.resize(squared, (shape, shape))

    # In case width and height are equal
    if(h==w):
        result = cv2.resize(img, (shape, shape))


    return result

# invert color of pixel between black and white
def invert(img):
    inverted = (255-img)
    return inverted
