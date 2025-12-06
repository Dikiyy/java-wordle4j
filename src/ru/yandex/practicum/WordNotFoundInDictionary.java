package ru.yandex.practicum;

public class WordNotFoundInDictionary extends Exception {

    public WordNotFoundInDictionary(String word) {
        super("Данного слова нет в словаре: " + word);
    }
}