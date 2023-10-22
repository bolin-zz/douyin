package com.douyin.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPhraseGenerator {public static void main(String[] args) {
    List<String> words = readWordsFromFile("E:\\projects2023\\e3huiman\\words.txt");

    if (words.isEmpty()) {
        System.out.println("No words found in the file.");
        return;
    }
    String randomPhrase = generateRandomPhrase(words);
    System.out.println("Random Phrase: " + randomPhrase);
}

    public static String generateRandomPhrase(List<String> words) {
        if (words.isEmpty()) {
            return "";
        }

        List<String> selectedWords = new ArrayList<>(words);
        StringBuilder phraseBuilder = new StringBuilder();
        Random random = new Random();

        while (!selectedWords.isEmpty()) {
            int randomIndex = random.nextInt(selectedWords.size());
            phraseBuilder.append(selectedWords.remove(randomIndex)).append(" ");
        }

        return phraseBuilder.toString().trim();
    }
    public static List<String> readWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }
}
