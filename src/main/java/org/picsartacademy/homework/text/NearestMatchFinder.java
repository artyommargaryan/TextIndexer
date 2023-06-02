package org.picsartacademy.homework.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NearestMatchFinder {
    private static final File file = new File("src/main/resources/texts/words_alpha.txt");
    private static final String[] suffixes = { "th", "st", "nd", "rd" };

    private NearestMatchFinder() {
    }

    public static String normalize(String word) {
        List<String> library = loadLibrary();

        if (containsNumber(word)) {
            System.out.println("Nearest match to \"" + word + "\": " + word);
            return word;
        }

        String nearestMatch = findNearestMatch(word, library);
        System.out.println("Nearest match to \"" + word + "\": " + nearestMatch);

        return nearestMatch;
    }

    private static boolean containsNumber(String str) {
        boolean containsDigit = false;
        boolean containsNonDigit = false;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
            } else {
                containsNonDigit = true;
            }
        }

        if (containsDigit && !containsNonDigit) {
            return true;
        } else if (!containsDigit && containsNonDigit) {
            return false;
        }

        return endsWithDigitAndSuffix(str);
    }

    public static boolean endsWithDigitAndSuffix(String str) {
        if (str.length() < 3) {
            return false;
        }

        String lastThreeChars = str.substring(str.length() - 3);

        if (!Character.isDigit(lastThreeChars.charAt(0))) {
            return false;
        }

        String suffix = lastThreeChars.substring(1);
        for (String validSuffix : suffixes) {
            if (suffix.equals(validSuffix)) {
                return true;
            }
        }

        return false;
    }
    private static List<String> loadLibrary() {
        List<String> library = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                library.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return library;
    }

    private static String findNearestMatch(String targetWord, List<String> library) {
        String nearestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (String word : library) {
            int distance = calculateLevenshteinDistance(targetWord, word);
            if (distance < minDistance) {
                minDistance = distance;
                nearestMatch = word;
            }
        }

        return nearestMatch;
    }

    private static int calculateLevenshteinDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
                }
            }
        }

        return dp[m][n];
    }
}