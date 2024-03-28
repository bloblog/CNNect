from tensorflow.keras.models import load_model
from nltk.tokenize import RegexpTokenizer
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
import numpy as np


def set_model():
    model = load_model('data/py/category_model.h5')
    return model

def make_lower_case(text):
    return text.lower()

def remove_punctuation(text):
    tokenizer = RegexpTokenizer(r'[a-zA-Z]+')
    text = tokenizer.tokenize(text)
    text = " ".join(text)
    return text

def preprocessing(scripts):
    cleaned_scripts = []
    for script in scripts:
        script = make_lower_case(script)
        script = remove_punctuation(script)
        cleaned_scripts.append(script)
    return cleaned_scripts

def set_padding(cleaned_scripts):
    max_length = 4000
    tokenizer = Tokenizer()
    tokenizer.fit_on_texts(cleaned_scripts)
    X_sequences = tokenizer.texts_to_sequences(cleaned_scripts)
    X_padded = pad_sequences(X_sequences, maxlen=max_length, padding='post', truncating='post')

    return X_padded
    
def data_generator(data, batch_size):
    num_batches = (len(data) - 1) // batch_size + 1  # 마지막 배치를 처리하기 위해 수정
    for i in range(num_batches):
        start_idx = i * batch_size
        end_idx = min((i + 1) * batch_size, len(data))
        batch_data = data[start_idx:end_idx]

        yield batch_data

def get_category(scripts):
    category = []
    model = set_model()
    
    cleaned_scripts = preprocessing(scripts)
    padded_scripts = set_padding(cleaned_scripts)

    generator = data_generator(padded_scripts, 32)
    for batch_data in generator:
        result = model.predict(batch_data)
        category += [np.argmax(pred) for pred in result]

    return category

# import json

# # JSON 파일 읽기
# with open('sample.json', 'r') as file:
#     data = json.load(file)

# print(get_category(data))
