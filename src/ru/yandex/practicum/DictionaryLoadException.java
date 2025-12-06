package ru.yandex.practicum;

public class DictionaryLoadException extends Throwable {
    public DictionaryLoadException(String ex) {
        super(ex);
    }

    public DictionaryLoadException(String ex, Throwable clause) {
        super(ex, clause);
    }
}
