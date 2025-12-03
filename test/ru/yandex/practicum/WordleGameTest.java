package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private WordleDictionary dict;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        dict = new WordleDictionary();
        dict.init(Arrays.asList(
                "герой",
                "гонец",
                "город",
                "столб"
        ));
        PrintWriter log = new PrintWriter(System.out, true);
        game = new WordleGame(dict, "герой", log);
    }

    @Test
    void makeMove_correctGuess_wins() throws Exception {
        String mask = game.makeMove("герой");
        assertEquals("+++++", mask);
        assertTrue(game.isWin());
        assertTrue(game.isFinished());
    }

    @Test
    void makeMove_wordNotInDictionary_throws() {
        assertThrows(WordNotFoundInDictionary.class,
                () -> game.makeMove("ххххх"));
    }

    @Test
    void suggestWord_respectsMasks() throws Exception {
        game.makeMove("гонец");
        String suggestion = game.suggestWord();
        assertNotNull(suggestion);

    }
}
