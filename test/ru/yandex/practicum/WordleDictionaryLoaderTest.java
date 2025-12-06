package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordleDictionaryLoaderTest {

    @Test
    void testValidFileLoad() throws Exception, DictionaryLoadException {
        // tmp
        File temp = File.createTempFile("dict-test", ".txt");
        try (FileWriter fw = new FileWriter(temp, StandardCharsets.UTF_8)) {
            fw.write("герой\nгород\nстолб\n");
        }

        PrintWriter log = new PrintWriter(System.out, true);
        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
        WordleDictionary dict = loader.load(temp.getAbsolutePath());

        assertTrue(dict.contains("герой"));
        assertTrue(dict.contains("город"));
    }

    @Test
    void testEmptyFileThrows() throws Exception {
        File temp = File.createTempFile("dict-empty", ".txt");

        PrintWriter log = new PrintWriter(System.out, true);
        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);

        assertThrows(DictionaryLoadException.class,
                () -> loader.load(temp.getAbsolutePath()));
    }
}
