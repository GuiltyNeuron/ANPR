# License plate detection and recognition using color segmentation and Tesseract
[![Licence](https://img.shields.io/github/license/GuiltyNeuron/ANPR?style=plastic)](https://github.com/GuiltyNeuron/ANPR/blob/master/LICENSE)
[![Stars](https://img.shields.io/github/stars/GuiltyNeuron/ANPR?style=social)](https://github.com/GuiltyNeuron/ANPR/stargazers)

In this work we present Dutch license plate detection based on color segmentation and recognition using Google Ocr engine Tesseract.
We will use python with the two libraries Opencv and Numpy.


### The pipeline below show steps followed :
![Licence_plate_detection_from_image](data/pipe.png)
#### How to use : 

````
python anpr.py --i data/images/1.jpg
````

you will find result under "/temp" and processing steps under "/temp/steps"

To detect LP from a video
````
python anpr.py --v video.mp4
````

#### Examples :

![Licence_plate_detection_from_image](data/exple1.jpg)
![Licence_plate_detection_from_image](data/exple2.jpg)
