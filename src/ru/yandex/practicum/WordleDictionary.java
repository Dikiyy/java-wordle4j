package ru.yandex.practicum;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;


    private String normalize(String word) {
        return word
                .trim()
                .toLowerCase(Locale.ROOT)
                .replace("ё", "е");
    }

    private boolean isRussian(String word) {
        return word.matches("[а-я]{5}");
    }

    public void init(List<String> words) {
        this.words = new ArrayList<>();
        for (String word : words) {
            String tmp = normalize(word);
            if (isRussian(tmp)) {
                this.words.add(tmp);
            }

        }
    }

    public boolean contains(String word) {
        return words.contains(normalize(word));
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public String compare(String answer, String guess) {
        if (answer == null || guess == null || answer.length() != 5 || guess.length() != 5) {
            throw new IllegalArgumentException("Words must be non-null and 5 letters long");
        }
        answer = normalize(answer);
        guess = normalize(guess);

        char[] result = {'-', '-', '-', '-', '-'};
        boolean[] usedInAnswer = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (answer.charAt(i) == guess.charAt(i)) {
                result[i] = '+';
                usedInAnswer[i] = true;
            }
        }


        for (int i = 0; i < 5; i++) {
            if (result[i] == '+') {
                continue;
            }
            char guessChar = guess.charAt(i);
            for (int j = 0; j < 5; j++) {
                if (!usedInAnswer[j] && answer.charAt(j) == guessChar) {
                    result[i] = '^';
                    usedInAnswer[j] = true;
                    break;
                }
            }
        }

        return new String(result);
    }

}
