package com.leman.anagram;

import static com.leman.anagram.WordUtils.sortStringChars;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combinations {
    private final StringBuilder output = new StringBuilder();
    private String inputString = null;
    private final List<String> allCombinations = new ArrayList<>();

    public String getInputString() {
        return inputString;
    }

    public List<String> getAllCombinations() {
        return allCombinations;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public Combinations() {
    }

    private void combine() {
        combine(0);
    }

    private void combine(int start) {

        for (int i = start; i < inputString.length(); ++i) {
            output.append(inputString.charAt(i));
            if ((output.length() != 1) && (output.length() != 2)) {
                allCombinations.add(output.toString());
            }
            if (i < inputString.length()) {
                combine(i + 1);
            }
            output.setLength(output.length() - 1);
        }
    }

    public Set<String> getUniqueSortedWords(final List<String> allCombinations) {
        combine();
        final Set<String> sortedWords = new HashSet<>();
        for (final String aCombination : allCombinations) {
            sortedWords.add(sortStringChars(aCombination));
        }
        return sortedWords;
    }

    public static void main(String args[]) {
        final Combinations combObj = new Combinations();
        combObj.setInputString("dexoiribonuclei");

        if (combObj.getInputString().length() <= 15) {
            final Set<String> uniqueSortedWords = combObj.getUniqueSortedWords(combObj.getAllCombinations());
            System.out.println(uniqueSortedWords);
            System.out.println(uniqueSortedWords.size());
        } else {
            System.out.println(combObj.getInputString().length());
        }
    }
}