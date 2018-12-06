import cv2
import numpy as np
import matplotlib.pyplot as plt



def resize_carre(img): # the input is a numpy array

    h = img.shape[0]
    w = img.shape[1]
    if(h>w):
        diff = h-w
        if( diff % 2 == 0):
            x1 = np.zeros(shape=(h,diff//2))
            x2 = x1
        else:
            x1 = np.zeros(shape=(h,diff//2))
            x2 = np.zeros(shape=(h,(diff//2)+1))


        result = np.concatenate((x1, img, x2), axis=1)
    if(h<w):
        diff = w-h
        if( diff % 2 == 0):
            x1 = np.zeros(shape=(diff//2,w))
            x2 = x1
        else:
            x1 = np.zeros(shape=(diff//2,w))
            x2 = np.zeros(shape=((diff//2)+1,w))
        result = np.concatenate((x1, img, x2), axis=0)
    return result
