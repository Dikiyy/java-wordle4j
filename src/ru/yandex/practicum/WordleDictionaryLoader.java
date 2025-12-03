package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WordleDictionaryLoader {

    private final PrintWriter pr;

    public WordleDictionaryLoader(PrintWriter pr) {
        this.pr = pr;
    }

    public WordleDictionary load(String Load) throws DictionaryLoadException{
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(Load), StandardCharsets.UTF_8))) {
            String line;
            while((line = br.readLine())!=null){
                words.add(line);
            }

        } catch (IOException e) {
            pr.println("Error loading dictionary from file " + Load + ": " + e.getMessage());
            throw new DictionaryLoadException("Failed to load dictionary", e);
        }
        if (words.isEmpty()) {
            throw new DictionaryLoadException("Dictionary is empty: " + Load);
        }
        WordleDictionary dict = new WordleDictionary();
        dict.init(words);
        return dict;
    }
}
