package org.picsartacademy.homework.text;

import org.picsartacademy.mylib.datastructures.list.LinkedList;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class Word {
    private final String word;
    private final LinkedList<String> fileNames = new LinkedList<>();

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String getFileNamesAsString() {
        return String.join(", ", fileNames);
    }

    public void addFileName(String fileName) {
        if (fileNames.contains(fileName)) {
            return;
        }
        fileNames.add(fileName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(" : ");
        Object[] array = fileNames.toArray();
        for (int i = 0; i < array.length - 1; ++i) {
            stringBuilder.append(array[i]).append(" -> ");
        }
        stringBuilder.append(array[array.length - 1]);

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Word word1)) {
            return false;
        }

        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
