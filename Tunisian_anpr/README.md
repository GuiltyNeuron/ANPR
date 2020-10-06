# Automatic License Plate Detection & Recognition using deep learning
[![Licence](https://img.shields.io/github/license/GuiltyNeuron/ANPR?style=plastic)](https://github.com/GuiltyNeuron/ANPR/blob/master/LICENSE)
[![Documentation](https://img.shields.io/badge/documentation-TowardsDataScience-blue)](https://towardsdatascience.com/automatic-license-plate-detection-recognition-using-deep-learning-624def07eaaf)
[![Dataset](https://img.shields.io/badge/Dataset-licence.plates-green)](https://www.kaggle.com/achrafkhazri/labeled-licence-plates-dataset)
[![Dataset](https://img.shields.io/badge/Dataset-plate.digits-yellowgreean)](https://www.kaggle.com/achrafkhazri/licence-plate-digits-dataset)
[![cfg](https://img.shields.io/badge/dependencies-yolo.weights-blueviolet)](https://www.kaggle.com/achrafkhazri/yolo-weights-for-licence-plate-detector)
[![Stars](https://img.shields.io/github/stars/GuiltyNeuron/ANPR?style=social)](https://github.com/GuiltyNeuron/ANPR/stargazers)

In this repos we study number plate detection and recognition using different deep learning models and computer vision approaches.

### Licence plate detection using Yolo :
In order to detect licence we will use Yolo ( You Only Look Once ) deep learning object detection architecture based on convolution neural networks.
This architecture was introduced by Joseph Redmon , Ali Farhadi, Ross Girshick and Santosh Divvala first version in 2015 and later version 2 and 3.

Yolo v1 : Paper [link](https://arxiv.org/pdf/1506.02640.pdf).

Yolo v2 : Paper [link](https://arxiv.org/pdf/1612.08242.pdf).

Yolo v3 : Paper [link](https://arxiv.org/pdf/1804.02767.pdf).

Yolo is a single network trained end to end to perform a regression task predicting both object bounding box and object class.
This network is extremely fast, it processes images in real-time at 45 frames per second. A smaller version of the network, tiny YOLO, processes an astounding 155 frames per second.

You will find more information about how to train Yolo on your customized dataset in this [Link](https://towardsdatascience.com/automatic-license-plate-detection-recognition-using-deep-learning-624def07eaaf).

There is also other Deep learning object detector that you can use such as Single Shot Detector (SSD) and Faster RCNN.
#### How to use : 
We used python v3.5.5
install requirement
````
pip install -r requirement.txt
````

Download Yolo weights from this [Link](https://www.kaggle.com/achrafkhazri/yolo-weights-for-licence-plate-detector).

Detect LP from an image
````
python detector.py --image test.jpg
````

To detect LP from a video
````
python detector.py --video test.mp4
````

#### Examples :

Detection from image :
![Licence_plate_detection_from_image](Licence_plate_detection/test_yolo_out_py.jpg)

### Licence plate recognition :

We are stadying Tunisian plates and USA plates for the recognition, check the sub folders in plates recognition folder!

