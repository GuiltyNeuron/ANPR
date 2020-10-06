import numpy as np
import cv2


def get_contour_precedence(contour, cols):
    tolerance_factor = 10
    origin = cv2.boundingRect(contour)
    return ((origin[1] // tolerance_factor) * tolerance_factor) * cols + origin[0]


def square(img):
    """
    This function resize non square image to square one (height == width)
    :param img: input image as numpy array
    :return: numpy array
    """

    # image after making height equal to width
    squared_image = img

    # Get image height and width
    h = img.shape[0]
    w = img.shape[1]

    # In case height superior than width
    if h > w:
        diff = h-w
        if diff % 2 == 0:
            x1 = np.zeros(shape=(h, diff//2))
            x2 = x1
        else:
            x1 = np.zeros(shape=(h, diff//2))
            x2 = np.zeros(shape=(h, (diff//2)+1))

        squared_image = np.concatenate((x1, img, x2), axis=1)

    # In case height inferior than width
    if h < w:
        diff = w-h
        if diff % 2 == 0:
            x1 = np.zeros(shape=(diff//2, w))
            x2 = x1
        else:
            x1 = np.zeros(shape=(diff//2, w))
            x2 = np.zeros(shape=((diff//2)+1, w))

        squared_image = np.concatenate((x1, img, x2), axis=0)

    return squared_image


def sort(vector):
    sort = True
    while (sort == True):

        sort = False
        for i in range(len(vector) - 1):
            x_1 = vector[i][0]
            y_1 = vector[i][1]

            for j in range(i + 1, len(vector)):

                x_2 = vector[j][0]
                y_2 = vector[j][1]

                if (x_1 >= x_2 and y_2 >= y_1):
                    tmp = vector[i]
                    vector[i] = vector[j]
                    vector[j] = tmp
                    sort = True

                elif (x_1 < x_2 and y_2 > y_1):
                    tmp = vector[i]
                    vector[i] = vector[j]
                    vector[j] = tmp
                    sort = True
    return vector
def plate_segmentation(img_file_path):

    img = cv2.imread(img_file_path)
    imgray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    height = img.shape[0]
    width = img.shape[1]
    area = height * width

    scale1 = 0.001
    scale2 = 0.1
    area_condition1 = area * scale1
    area_condition2 = area * scale2
    # global thresholding
    ret1,th1 = cv2.threshold(imgray,127,255,cv2.THRESH_BINARY)

    # Otsu's thresholding
    ret2,th2 = cv2.threshold(imgray,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)

    # Otsu's thresholding after Gaussian filtering
    blur = cv2.GaussianBlur(imgray,(5,5),0)
    ret3,th3 = cv2.threshold(blur,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)

    contours, hierarchy = cv2.findContours(th3, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    # sort contours
    contours = sorted(contours, key=cv2.contourArea, reverse=True)

    cropped = []
    for cnt in contours:
        (x,y,w,h) = cv2.boundingRect(cnt)


        if (w * h > area_condition1 and w * h < area_condition2 and w/h > 0.3 and h/w > 0.3):
            cv2.drawContours(img, [cnt], 0, (0, 255, 0), 3)
            cv2.rectangle(img, (x,y), (x+w,y+h), (255, 0, 0), 2)
            c = th2[y:y+h,x:x+w]
            c = np.array(c)
            c = cv2.bitwise_not(c)
            c = square(c)
            c = cv2.resize(c,(28,28), interpolation = cv2.INTER_AREA)
            cropped.append(c)
    cv2.imwrite('detection.png', img)
    return cropped
