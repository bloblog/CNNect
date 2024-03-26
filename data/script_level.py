import math
import pandas as pd

sentence_cutline = [42.13323525303835, 45.31457886044376, 50.02139772682483, 53.98793352312772, 56.91700066393434]
word_cutline = [1.75, 1.85, 1.92, 1.96, 2.00]
cefr_levels = ['A1', 'A2', 'B1', 'B2', 'C1', 'C2']
word_csv = pd.read_csv('/content/sample_data/result.csv')


# 문장 기반 난이도 추출
def make_lower_case(text):
    return text.lower()


def getTotalSyllableCnt(text):
    syllable_count = 0
    vowels = "aeiouAEIOU"
    for char in text:
        if char in vowels:
            syllable_count += 1
    return syllable_count


def getTotalWordCnt(text):
    words = text.split()
    return len(words)


def find_sentence_difficulty_section(flesh_ease, cutlines, reverse=False):
    ranking = 0
    for i, cutline in enumerate(cutlines):
        if flesh_ease <= cutline:
            ranking = i + 1
            break
    if ranking == 0:  # 모든 cutline보다 큰 경우, 마지막 구간으로 분류
        ranking = len(cutlines) + 1

    if reverse:
        return len(cutlines) + 2 - ranking  # 순위 뒤집기
    else:
        return ranking


def calculate_difficulty_by_sentence(script, sentences) -> float:
    total_sentences = len(sentences)
    total_syllable = getTotalSyllableCnt(script)
    total_words = getTotalWordCnt(script)

    if total_sentences == 0 or total_words == 0:
        return None

    flesh_ease = 206.835 - 1.015 * (total_words / total_sentences) - 84.6 * (total_syllable / total_words)
    difficulty = find_sentence_difficulty_section(flesh_ease, sentence_cutline, reverse=True)
    return difficulty


# 단어 기반 난이도 추출
def find_word_difficulty_section(flesh_ease, cutlines):
    for i, cutline in enumerate(cutlines):
        if flesh_ease <= cutline:
            return i + 1  # 구간 번호를 숫자로 반환
    return len(cutlines) + 1


def calculate_difficulty_by_word(script) -> float:
    paragraph = script.lower().split()
    filtered_words_by_level = {}
    for level in cefr_levels:
        # 현재 레벨에 해당하는 행만 필터링
        filtered_df = word_csv[word_csv['CEFR'] == level]

        # 필터링된 데이터프레임의 'headword' 컬럼에서 스크립트의 단어 찾기
        filtered_words = [word for word in paragraph if word in filtered_df['headword'].tolist()]

        # 현재 CEFR 레벨에 대한 결과를 딕셔너리에 저장
        filtered_words_by_level[level] = set(filtered_words)

    weighted_sum = (len(filtered_words_by_level["A1"]) * 1 + len(filtered_words_by_level["A2"]) * 2 + len(
        filtered_words_by_level["B1"]) * 3 + len(filtered_words_by_level["B2"]) * 4
                    + len(filtered_words_by_level["C1"]) * 5 + len(filtered_words_by_level["C2"]) * 6)
    total_words = sum(len(group) for group in filtered_words_by_level.values())
    final_value = weighted_sum / total_words if total_words > 0 else 0

    difficulty = find_word_difficulty_section(final_value, word_cutline)
    return difficulty


def calculate_difficulty(full_script, script_sentence) -> int:
    sentence = calculate_difficulty_by_sentence(full_script, script_sentence)
    word = calculate_difficulty_by_word(full_script)

    average_difficulty = (sentence + word) / 2

    # word가 sentence보다 크거나 같으면 결과를 올림
    if word >= sentence:
        return math.ceil(average_difficulty)
    # 그렇지 않으면 결과를 내림
    else:
        return math.floor(average_difficulty)


def find_word_list(script, level):
    level_index = int(level) - 1
    paragraph = script.lower().split()
    found_words = set()
    current_level = level_index

    # 현재 레벨에서 시작하여 필요한 단어 수를 충족할 때까지 하위 레벨로 이동
    while current_level >= 0 and len(found_words) < 10:
        # 현재 레벨의 단어 필터링
        filtered_df = word_csv[word_csv['CEFR'] == cefr_levels[current_level]]
        filtered_words = [word for word in paragraph if word in filtered_df['headword'].tolist()]
        found_words.update(filtered_words)
        # B1 이상 레벨에서만 하위 레벨의 단어를 추가하도록 함
        if level_index >= 2:
            current_level -= 1
        else:
            break  # A2 또는 A1 레벨에서는 더 이상 하위 레벨로 이동하지 않음

    return found_words
