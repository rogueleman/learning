/**
 * 
 */
package com.leman.anagram;

/**
 * @author ggutau
 *
 */
public class WordUtils {
	
    public static String sortStringChars(String unsorted) {
    	char[] content = unsorted.toCharArray();
    	java.util.Arrays.sort(content);
    	return new String(content);
    }

}
