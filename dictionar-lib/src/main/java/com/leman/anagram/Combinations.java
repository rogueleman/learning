package com.leman.anagram;

import java.util.ArrayList;
import java.util.List;

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

    public void combine() {
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

    public static void main(String args[]) {
        final Combinations combObj = new Combinations();
        combObj.setInputString("dezoxiribonucle");

        if (combObj.getInputString().length() <= 15) {
            combObj.combine();
            System.out.println(combObj.getAllCombinations());
            System.out.println(combObj.getAllCombinations().size());
        } else {
            System.out.println(combObj.getInputString().length());
        }

    }
}