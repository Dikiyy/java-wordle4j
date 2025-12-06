package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary dict;

    @BeforeEach
    void setUp() {
        dict = new WordleDictionary();
        dict.init(Arrays.asList(
                "герой",
                "ГОРОД",
                "ёжик",
                "столб ",
                "  ёлка "
        ));
    }

    @Test
    void initFiltersAndNormalizesWords() {
        assertTrue(dict.contains("герой"));
        assertTrue(dict.contains("город"));
        assertTrue(dict.contains("столб"));

        assertFalse(dict.contains("ёжик"));
        assertFalse(dict.contains("ёлка"));
    }

    @Test
    void compareSimpleCase() {
        String mask = dict.compare("герой", "гонец");
        assertEquals("+^-^-", mask);
    }
}
