import pandas as pd
import numpy as np
import cv2
import os
import pickle


# List of images
data = []

# List of labels
labels = []

input_path = 'dataset'
# Load all directory
for root, dirs, files in os.walk(input_path):

    # Filter every folder
    for dir in dirs:

        print(" Class : \t \t " + dir)

        # Filter all files in the directory
        for filename in os.listdir(input_path + "/" + dir):

            # Make sure that our file is text
            if filename.endswith('.jpg'):

                img = cv2.imread(input_path + "/" + dir + "/" + filename)

                gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

                data.append(gray)
                labels.append(dir)

# Save test data and labels
pickle.dump(data, open("data.pickle", "wb"))
pickle.dump(labels, open("labels.pickle", "wb"))

print('Length data : ' + str(len(data)))
print('Length labels : ' + str(len(labels)))
print('Processs finished !')
