package org.picsartacademy.homework.text;

import org.picsartacademy.mylib.datastructures.list.LinkedList;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

public class TextV2 {
    private final LinkedList<Word>[] text;
    private final int capacity = 16;

    @SuppressWarnings("unchecked")
    public TextV2() {
        text = new LinkedList[capacity];
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

        int wordHash = hash(normalizedWord);

        if (text[wordHash] == null) {
            text[wordHash] = new LinkedList<>();
            word1.addFileName(fileName);
            text[wordHash].add(word1);
            return;
        }

        Optional<Word> search = search(word1, wordHash);
        if (search.isPresent()) {
            search.get().addFileName(fileName);
            return;
        }

        word1.addFileName(fileName);
        text[wordHash].add(word1);
    }

    private Word addWord(String word) {
        int wordHash = hash(word);
        Word word1 = new Word(word);
        if (text[wordHash] == null) {
            text[wordHash] = new LinkedList<>();
        }
        text[wordHash].add(word1);
        return word1;
    }

    private int hash(String word) {
        int sum = 0;

        for (int i = 0; i < word.length(); i++) {
            sum += word.charAt(i);
        }

        return sum % capacity;
    }

    private Optional<Word> search(Word word, int wordHash) {
        return Arrays.stream(text[wordHash].toArray())
                .filter((w) -> w.equals(word))
                .findFirst().map((w) -> (Word) w);
    }

    public String search(String word) {
        String normalizedWorld = NearestMatchFinder.normalize(word);
        int wordHash = hash(normalizedWorld);

        Optional<Word> search = search(new Word(word), wordHash);

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

        for (var words : text) {
            if (words != null) {
                for (var word : words) {
                    buffer.append(word).append("\n");
                }
            }
        }
        return buffer;
    }

    public void deserialize(File file) {
        String line;
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" : ");
                String[] split1 = split[1].split(" -> ");
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
