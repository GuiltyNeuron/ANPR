from engine import detect, process, recognise, detect_belg, post_process
import cv2
import numpy
import argparse
import os
import glob
import sys
import time

parser = argparse.ArgumentParser()
parser.add_argument('--i', '-image', help="Input image path", type= str)
parser.add_argument('--v', '-video', help="Input video path", type= str)


args = parser.parse_args()
abs_path = os.path.dirname(sys.executable)


if args.i:
    start = time.time()
    try:
        os.mkdir('temp')
    except:
        files = glob.glob('tmp')
        for f in files:
            os.remove(f)

    input_image = cv2.imread(args.i)
    detection, crops = detect(input_image)

    i = 1
    for crop in crops:

        crop = process(crop)

        cv2.imwrite('temp/crop' + str(i) + '.jpg', crop)
        recognise('temp/crop' + str(i) + '.jpg', 'temp/crop'+str(i))
        post_process('temp/crop' + str(i) + '.txt')
        i += 1
    cv2.imwrite('temp/detection.jpg', detection)
    finish = time.time()
    print('Time processing >>>>>>  '+ str(finish-start))
elif args.v:
    cap = cv2.VideoCapture(args.v)

    # Check if camera opened successfully
    if cap.isOpened() == False:
        print("Error opening video stream or file")

    while (cap.isOpened()):
        # Capture frame-by-frame
        ret, frame = cap.read()
        if ret == True:

            frame, crop = detect(frame)
            # Display the resulting frame

            cv2.putText(frame, 'Press \'Q\' to exit !',(50, 50),cv2.FONT_HERSHEY_SIMPLEX,1,(0, 0, 255), 2)
            cv2.imshow('Frame', frame)

            # Press Q on keyboard to  exit
            if cv2.waitKey(25) & 0xFF == ord('q'):
                break

        # Break the loop
        else:
            break

    # When everything done, release the video capture object
    cap.release()

    # Closes all the frames
    cv2.destroyAllWindows()

else:
	print("--i : input image file path\n--v : input video file path")
	