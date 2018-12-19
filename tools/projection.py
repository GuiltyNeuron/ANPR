import numpy as np
import cv2
import time
import glob, os



def horizontalProjection(img):

        # Variables declaration

        WHITE = [255,255,255]
        BLACK = [0,0,0]

        k1 = np.ones((5,5),np.float32)/25 # Kernel for thresh filter
        k2 = np.ones((8,15),np.uint8)    # Kernel for MORPH_OPEN
        k3 = np.ones((1,15),np.uint8)     # Kernel for MORPH_CLOSE

        f = 0
        max = 0
        m = 0

        t1 = list()
        blocList =list()

        img= cv2.copyMakeBorder(img,100,100,100,100,cv2.BORDER_CONSTANT,value=WHITE)
        gray_image = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        ret,thresh = cv2.threshold(gray_image,127,255,cv2.THRESH_BINARY)
        dst = cv2.filter2D(thresh,-1,k1)
        dilation = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, k3, iterations = 1)
        #dilation = cv2.dilate(thresh,k2,iterations = 2)
        dilation = cv2.morphologyEx(dilation, cv2.MORPH_OPEN, k2, iterations = 10)
        # rotation
        d = np.rot90(dilation,2)
        # compute the sommation
        y_sum = cv2.reduce(d, 1, cv2.REDUCE_SUM, dtype=cv2.CV_32S)
        # rotate the vector x_sum
        # get height and weight
        x = thresh.shape[1]
        y = thresh.shape[0]
        # division the result by height and weight
        y_sum = y_sum/x
        # x_arr and y_arr are two vector with lenght weight and height to plot histogram projection properly
        #y_arr = np.arange(y)
        # convert y_arr to numpy array

        # convert to zero small details and 1 for needed details
        y_sum[y_sum <= 240] = 1
        y_sum[y_sum >240] = 0


        ff = y_sum[0]
        for i in range(y_sum.size):
                if(y_sum[i] != ff):

                    ff = y_sum[i]
                    t1.append(i-1)
        rect_h = np.array(t1)      # array of changing pixel value 1 to 0 or 2 to 1
        for i in range(len(rect_h) - 1):
            if(y_sum[rect_h[i]] == 0):
                blocList.append([rect_h[i],rect_h[i+1]]) # cutting nb (image) and adding each slice to the list caracrter_list_image

        for i in range(len(blocList)):
            j = blocList[i][1] - blocList[i][0]
            if (j > max):
                max = j
                m = i
        #================================================================================
        result = np.rot90(img,2)
        result = result[blocList[m][0]:blocList[m][1],:]
        result = np.rot90(result,2)

        return result

def verticalProjection(img):
    k1 = np.ones((5,5),np.float32)/25
    k2 = np.ones((10,10),np.uint8)
    k3 = np.ones((15,1),np.uint8)

    gray_image = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    ret,thresh = cv2.threshold(gray_image,127,255,cv2.THRESH_BINARY)
    dst = cv2.filter2D(thresh,-1,k1)
    #dilation = cv2.dilate(thresh,k2,iterations = 2)
    dilation = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, k3, iterations = 1)
    dilation = cv2.morphologyEx(dilation, cv2.MORPH_OPEN, k2, iterations = 10)
    # compute the sommation
    x_sum = cv2.reduce(dilation, 0, cv2.REDUCE_SUM, dtype=cv2.CV_32S)
    # rotate the vector x_sum
    x_sum = x_sum.transpose()
    # get height and weight
    x = thresh.shape[1]
    y = thresh.shape[0]
    # division the result by height and weight
    x_sum = x_sum/y
    # x_arr and y_arr are two vector with lenght weight and height to plot histogram projection properly
    #x_arr = np.arange(x)

    # convert to zero small details
    x_sum[x_sum <250] = 1
    x_sum[x_sum >250] = 0

    t2 = list()
    f = 0
    ff = x_sum[0]

    if (x_sum[0] == 1):
        t2.append(0)
    for i in range(x_sum.size):
            if(x_sum[i] != ff):
                ff = x_sum[i]
                t2.append(i-1)
    if (x_sum[x_sum.size-1] == 1):
        t2.append(x_sum.size-1)
    rect_v = np.array(t2)

    blocList2 =list()

    for i in range(len(rect_v) - 1):
        if(x_sum[rect_v[i]+1] == 1):
            # cutting nb (image) and adding each slice to the list caracrter_list_image
            blocList2.append([rect_v[i],rect_v[i+1]])

    max2 = 0
    m2 = 0
    for i in range(len(blocList2)):
        j = blocList2[i][1] - blocList2[i][0]
        if (j > max2):
            max2 = j
            m2 = i

    #================================================================================
    result = img
    result = result[:,blocList2[m2][0]:blocList2[m2][1]]
    return result


#++++++++++++++ MAIN ++++++++++++++++++++++++++++++
