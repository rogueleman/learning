package com.leman.anagram;

import static java.util.Arrays.sort;

/**
 * @author ggutau
 */
public class WordUtils {

    public static String sortStringChars(final String unsorted) {
        final char[] content = unsorted.toLowerCase().toCharArray();
        sort(content);
        return new String(content);
    }
}