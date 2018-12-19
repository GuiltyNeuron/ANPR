import numpy as np
import cv2
from matplotlib import pyplot as plt
import time
from projection import horizontalProjection, verticalProjection
import glob, os
from morph import get
#start_time = time.time()
WHITE = [255,255,255]
BLACK = [0,0,0]

os.chdir("./input/test/")

for file in glob.glob("*.tif"):
    print(file)
    try:
        start_time = time.time()
        img = cv2.imread(file)

        morph = get(img)
        print("---Processing time:    %s seconds ---" % (time.time() - start_time))

        cv2.imwrite("___"+file, morph, [int(cv2.IMWRITE_TIFF_XDPI), 600, int(cv2.IMWRITE_TIFF_YDPI), 600, int(cv2.IMWRITE_TIFF_RESUNIT), 600])
        print("+++++++++++++++++++++++++ DONE +++++++++++++++++++++++++")
    except Exception as e:

        print("=========================Exception!!!!!! ===========================   ",e)
        pass
