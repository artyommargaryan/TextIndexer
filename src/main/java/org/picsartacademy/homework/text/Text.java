package org.picsartacademy.homework.text;

import java.io.*;
import java.util.*;

public class Text {
    private final Set<Word> text = new HashSet<>();

    public Text() {
    }

    public void addWord(String word, String fileName) {
        String cleared = clear(word);

        if (cleared.equals("")) {
            return;
        }

        String normalizedWord = NearestMatchFinder.normalize(cleared);
        Word word1 = new Word(normalizedWord);
        if (normalizedWord.equals("")) {
            return;
        }

        Optional<Word> search = search(word1);
        if (search.isPresent()) {
            search.get().addFileName(fileName);
            return;
        }

        word1.addFileName(fileName);
        text.add(word1);
    }

    private Word addWord(String word) {
        Word word1 = new Word(word);
        text.add(word1);
        return word1;
    }

    private Optional<Word> search(Word word1) {
        return Arrays.stream(text.toArray())
                .filter((w) -> w.equals(word1))
                .findFirst().map((w) -> (Word) w);
    }

    public String search(String word) {
        Optional<Word> search = search(new Word(word));

        if (search.isEmpty()) {
            return "No such word";
        }

        return search.get().getFileNamesAsString();
    }

    private String clear(String s) {
        return s.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
    }

    public StringBuffer serialize() {
        StringBuffer buffer = new StringBuffer();

        for (var word : text) {
            buffer.append(word).append("\n");
        }

        return buffer;
    }

    public void deserialize(File file) {
        String line;
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" : ");
                String[] split1 = split[1].split(" ");
                Word word = this.addWord(split[0]);
                for (var fileName : split1) {
                    word.addFileName(fileName);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (var word : text) {
            stringBuilder.append(word).append("\n");
        }

        return stringBuilder.toString();
    }
}
