package ru.yandex.practicum;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter("wordle.log", StandardCharsets.UTF_8)) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dict = loader.load("words_ru.txt");
            String answer = dict.getRandomWord();
            WordleGame game = new WordleGame(dict, answer, log);

            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
            System.out.println("Приветсвуем вас в игре, 5 букв. Кирилица, Enter - подсказка");

            while (!game.isFinished()) {
                System.out.println("Осталось попыток: " + game.getStepsLeft());
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    String suggestion = game.suggestWord();
                    if (suggestion == null) {
                        System.out.println("Нет доступных подсказок.");
                    } else {
                        System.out.println("Подсказка: " + suggestion);
                    }
                    continue;
                }

                try {
                    String mask = game.makeMove(input);
                    String normalized = input.trim().toLowerCase().replace('ё', 'е');
                    System.out.println(normalized);
                    System.out.println(mask);
                } catch (WordNotFoundInDictionary e) {
                    System.out.println("Такого слова нет в словаре.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (game.isWin()) {
                System.out.println("Поздравляем! Вы отгадали слово: " + game.getAnswer());
            } else {
                System.out.println("Ходы закончились. Загаданное слово: " + game.getAnswer());
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка. Смотрите файл wordle.log");
            try (PrintWriter errLog = new PrintWriter("wordle.log", StandardCharsets.UTF_8)) {
                e.printStackTrace(errLog);
            } catch (Exception ignored) {

            }
        } catch (DictionaryLoadException e) {
            throw new RuntimeException(e);
        }
    }
}
