package com.coursework.clinicregistration.Algorithms;

public class LinearSearch implements TextSearch {
    @Override
    public int search(String text, String word) {
        if (text.length() < word.length()) return -1;
        int i = 0;
        while (i + word.length() - 1 < text.length()) {
            boolean correctWord = true;
            for (int j = 0; j < word.length(); j++) {
                if (word.charAt(j) != text.charAt(i + j)) {
                    correctWord = false;
                    j = word.length();
                }
            }
            if (correctWord) return i;
            i++;
        }
        return -1;
    }
}
