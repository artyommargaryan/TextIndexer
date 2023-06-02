package org.picsartacademy.homework;

import org.picsartacademy.homework.text.Text;
import org.picsartacademy.homework.text.TextV2;

import java.io.*;
import java.util.Scanner;

public class TextSaver {
    private static TextV2 text = new TextV2();

    public static void main(String[] args) {
        File file1 = new File("src/main/resources/texts/1.txt");
        File file2 = new File("src/main/resources/texts/2.txt");
        File file3 = new File("src/main/resources/texts/db.txt");

        read(file1, file2);
        write(text.serialize(), file3);
    }

    private static void write(StringBuffer buffer, File file) {
        try(FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
          bufferedWriter.write(buffer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void read(File... files) {
        if (files == null) {
            throw new RuntimeException("file is null");
        }

        for (var file : files) {
            String fileName = file.getName();
            try (FileReader fileReader = new FileReader(file); Scanner scanner = new Scanner(fileReader)) {
                while (scanner.hasNext()) {
                    String str = scanner.next();
                    text.addWord(str, fileName);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}