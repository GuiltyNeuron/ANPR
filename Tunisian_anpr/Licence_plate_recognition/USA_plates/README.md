# USA licence plates recognition

In order to read USA plates we detect digits using connected contours then padding and reshaping to have the same input
shape and finally we use a convolution neural network fro prediction.
![detection](detection.png)
### Dataset :

Dataset contains majus alphabets and digits with the shape 28*28 for each.
We have 36 classes (A-Z, 0-9), 1016 samples for each class.
#### Training : 
We trained a CNN model, and we've got 96,6 accuracy
![accuracy](accuracy.png) ![loss](loss.png)


#### results :
Model doesn't give a good results because data does not have the same variation of orientations and noises.
We have to add some noises and orientation to our data.
