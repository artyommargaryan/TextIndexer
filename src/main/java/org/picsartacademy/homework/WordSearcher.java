package org.picsartacademy.homework;

import org.picsartacademy.homework.text.Text;
import org.picsartacademy.homework.text.TextV2;

import java.io.*;

public class WordSearcher {
    public static TextV2 text = new TextV2();
    public static void main(String[] args) {
        File file = new File("src/main/resources/texts/db.txt");
        text.deserialize(file);
        inputWord();
    }

    private static void inputWord() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String word;
        System.out.println("Enter word (Press Enter to exit):");
        try {
            while ((word = bufferedReader.readLine()) != null && !word.equals("")) {
                System.out.println(text.search(word));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
