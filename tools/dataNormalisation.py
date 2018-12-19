""" dataNormalisation.
This file convert a non normalised dataset to a normalised one where images have the same shape and processed with some
modifications and save two numpy arrays, one contain features and the other contain the appropeiate label.
Links:

Author: Achraf Khazri
Project:
"""

# ------------------------------------------------------------------
#
# THIS FILE HAS BEEN RENAMED 'dataNormalisation.py', FOR SIMPLICITY.
#
# ------------------------------------------------------------------

import numpy as np
import cv2 as cv
import os
from tools import resizeSquare, invert
import sys
from glob import glob


# Images folder path
directory = "./dir/cropped"

# List where features will be stored
features_list = []

# List where labels will be stored
features_label = []

# Create output folder if NOT exist
try:
    os.stat("./normalized_data")
except:
    os.mkdir("./normalized_data")

# Load all directory from the unnormalized dataset images
for root, dirs, files in os.walk(directory):

    # Filter every folder
    for dir in dirs:
        print(dir)

        # Create similar folder for storing normalized images if NOT exist
        try:
            os.stat("./normalized_data"+ "/" + dir + "/")
        except:
            os.mkdir("./normalized_data"+ "/" + dir + "/")

        # Name for the normalized image
        i = 0

        # Filter all files in the directory
        for filename in os.listdir(directory + "/" + dir):

            # Make sure that our file is an image
            if (filename.endswith('.png') or filename.endswith('.jpg') or filename.endswith('.tiff')):

                # Read the image
                image = cv.imread(directory + "/" + dir + "/" + filename)

                # Convert from RGB to Grayscale
                gray = cv.cvtColor(image,cv.COLOR_BGR2GRAY)

                # Threshold the image
                ret,binary = cv.threshold(gray, 100, 255, cv.THRESH_OTSU)

                # Invert image Black to white and white to black
                inverted = invert(binary)

                # Make the image in square form without distorsion
                squared = resizeSquare(inverted,100)

                # Transform the image from 2D vector to 1D vector
                vector = np.array(squared).ravel()

                # Add the feature vector to the feature_List
                features_list.append(vector)

                # Add the feature label to the features_label

                features_label.append(dir)

                # Save the image in the appropriate folder

                cv.imwrite("./normalized_data"+ "/" + dir + "/" +  str(i) +".jpg", squared)
                i= i+1

# feature_List and features_label as numpy array
X_train = np.array(features_list)
y_train = np.array(features_label)

# Save the features and their labels in numpy file
np.save('features', X_train)
np.save('labels', y_train)
