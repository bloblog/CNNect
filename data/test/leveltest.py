import pandas as pd
import random

from flask import Flask, Blueprint, jsonify

level_bp = Blueprint('level', __name__, url_prefix='/data/level')
app = Flask(__name__)

if __name__ == '__main__':
    app.run()


def generate_translation_lists(df, level):
    # 'CEFR' 열이 level인 행 3개 선택
    filtered_df = df[df['CEFR'] == level]
    random_rows = filtered_df.sample(n=3, replace=False)

    results = []

    for _, row in random_rows.iterrows():
        original_word = row['headword']
        original_translation = row['Translation']
        pos = row['pos']
        # 자기 자신을 제외하고 동일한 'pos'를 가진 행들 중 4개 무작위 선택
        same_pos_rows = df[(df['pos'] == pos) & (df['headword'] != original_word)].sample(
            n=min(3, len(df[(df['pos'] == pos) & (df['headword'] != original_word)])), replace=False)

        selected_translations = [original_translation] + same_pos_rows['Translation'].tolist()
        # 원본 영어 단어를 리스트의 첫 번째 요소로 추가
        selected_translations.insert(0, original_word)
        # 결과 리스트에 추가
        results.append(selected_translations)

    return results


@level_bp.route('/user/<level>')  # 3
def showUserId(level):  # 4
    df = pd.read_csv('./data/csv/result.csv')
    translation_lists = generate_translation_lists(df, level)

    # 결과를 저장할 리스트 초기화
    result_list = []

    # 생성된 단어 목록을 바탕으로 JSON 형태의 데이터 생성
    for translation_list in translation_lists:
        word_content = translation_list[0]
        word_answer = translation_list[1]
        # 나머지 단어들 선택 (처음 2개 제외)
        remaining_words = translation_list[2:]
        # 나머지 단어들을 랜덤하게 섞음
        all_words = [word_answer] + remaining_words
        random.shuffle(all_words)
        # 결과 JSON 객체 생성
        result = {
            "word_id": result_list.__len__() + 1,  # 예시로 ID를 리스트 길이+1로 설정
            "word_content": word_content,
            "word_answer": word_answer,
            "word_mean": all_words[:4],  # word_answer 포함하여 총 4개 선택
        }
        result_list.append(result)

    # 결과 리스트를 JSON 형태로 변환하여 반환
    return jsonify(result_list)
