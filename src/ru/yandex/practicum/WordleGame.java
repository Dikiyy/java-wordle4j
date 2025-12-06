package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {

    private final String answer;
    private final WordleDictionary dictionary;
    private final PrintWriter log;

    private final int maxSteps;
    private int stepsLeft;
    private boolean win;

    private final List<String> guesses = new ArrayList<>();
    private final List<String> masks = new ArrayList<>();
    private final Map<String, Integer> usedSuggestions = new LinkedHashMap<>();

    public WordleGame(WordleDictionary dictionary, String answer, int maxSteps, PrintWriter log) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary must not be null");
        }
        if (answer == null || answer.length() != 5) {
            throw new IllegalArgumentException("Answer must be 5 letters long");
        }
        this.dictionary = dictionary;
        this.answer = answer;
        this.maxSteps = maxSteps;
        this.stepsLeft = maxSteps;
        this.log = log;
    }

    public WordleGame(WordleDictionary dictionary, String answer, PrintWriter log) {
        this(dictionary, answer, 6, log);
    }

    public String makeMove(String guess) throws WordNotFoundInDictionary {
        if (isFinished()) {
            throw new IllegalStateException("Game is already finished");
        }

        if (guess == null) {
            throw new IllegalArgumentException("Слово не может быть null");
        }

        String normalized = guess.trim()
                .toLowerCase()
                .replace('ё', 'е');

        if (normalized.length() != 5 || !normalized.matches("[а-я]{5}")) {
            throw new IllegalArgumentException("Слово должно быть из 5 русских букв");
        }

        if (!dictionary.contains(normalized)) {
            throw new WordNotFoundInDictionary(normalized);
        }

        stepsLeft--;
        guesses.add(normalized);

        String mask = dictionary.compare(answer, normalized);
        masks.add(mask);

        if (log != null) {
            log.println("Guess: " + normalized + " -> " + mask + ", stepsLeft=" + stepsLeft);
        }

        if (normalized.equals(answer)) {
            win = true;
        }

        return mask;
    }

    public String suggestWord() {

        for (String candidate : dictionary.getWords()) {

            if (guesses.contains(candidate) || usedSuggestions.containsKey(candidate)) {
                continue;
            }

            usedSuggestions.put(candidate, usedSuggestions.getOrDefault(candidate, 0) + 1);

            boolean ok = true;

            for (int i = 0; i < guesses.size(); i++) {
                if (!fitsMask(candidate, guesses.get(i), masks.get(i))) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                usedSuggestions.put(candidate, usedSuggestions.getOrDefault(candidate, 0) + 1);
                if (log != null) {
                    log.println("Suggestion: " + candidate);
                }
                return candidate;
            }
        }

        return null;
    }

    private boolean fitsMask(String candidate, String guess, String mask) {
        candidate = candidate.toLowerCase().replace('ё', 'е');
        guess = guess.toLowerCase().replace('ё', 'е');

        for (int i = 0; i < 5; i++) {
            if (mask.charAt(i) == '+') {
                if (candidate.charAt(i) != guess.charAt(i)) {
                    return false;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (mask.charAt(i) == '^') {
                if (candidate.charAt(i) == guess.charAt(i)) {
                    return false;
                }
                if (candidate.indexOf(guess.charAt(i)) == -1) {
                    return false;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (mask.charAt(i) == '-') {
                char ch = guess.charAt(i);

                boolean letterMustAppear = false;
                for (int j = 0; j < 5; j++) {
                    if (mask.charAt(j) == '+' || mask.charAt(j) == '^') {
                        if (guess.charAt(j) == ch) {
                            letterMustAppear = true;
                            break;
                        }
                    }
                }

                if (!letterMustAppear && candidate.indexOf(ch) != -1) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isFinished() {
        return win || stepsLeft <= 0;
    }

    public boolean isWin() {
        return win;
    }

    public int getStepsLeft() {
        return stepsLeft;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getGuesses() {
        return Collections.unmodifiableList(guesses);
    }
}