## Licence Plate recognition

To train models run this command
datase : dataset folder path

`` $ python main.py -mode train -d dataset``

To recognize licence plate run this command

test.jpg : input image (licence plate)

mlp.pkl : MLP trained model

`` $ python main.py -mode predict -i test.jpg -model mlp.pkl``