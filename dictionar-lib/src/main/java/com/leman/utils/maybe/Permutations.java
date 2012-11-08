package com.leman.utils.maybe;

import java.util.Set;
import java.util.TreeSet;


public class Permutations {
	
    public static Set<String> s = new TreeSet<String>();
    
    public static void getPermutations(char[] a, int myCharSize) { 
    	findEnumarations(a, a.length, myCharSize); }

    private static void findEnumarations(char[] a, int n, int myCharSize) {

    	StringBuffer finalStringBuffer = new StringBuffer();
        if (myCharSize >= 2) {
            for (int i = 0; i <myCharSize; i++){
                finalStringBuffer.append(a[i]);
            }
            String finalString = finalStringBuffer.toString(); 
/*
 * in limba romana putem avea urmatoarele litere duplicate(de forma xx) intr-un cuvant
 * cc(accident), ee(idee), ii(copii), nn(innorat), oo(alcool)
 * daca avem duplicate alte litere in cuvantul aflat prin permutari => nu avem cuvant 
 */            
//            if (!finalString.contains("aa") && !finalString.contains("bb")  
//            && !finalString.contains("dd") && !finalString.contains("ff")
//            && !finalString.contains("gg") && !finalString.contains("hh")
//            && !finalString.contains("jj") && !finalString.contains("kk")
//            && !finalString.contains("ll") && !finalString.contains("mm")
//            && !finalString.contains("pp") && !finalString.contains("qq")
//            && !finalString.contains("rr") && !finalString.contains("ss")
//            && !finalString.contains("tt") && !finalString.contains("uu")
//            && !finalString.contains("vv") && !finalString.contains("ww")
//            && !finalString.contains("xx") && !finalString.contains("yy")
//            && !finalString.contains("zz") 
//            ){
            	s.add(sortStringChars(finalString));
//            }
        }
        
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            findEnumarations(a, n-1, myCharSize);
            swap(a, i, n-1);
         }
    }

    public static void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {        
    	StopWatch stopwatch = new StopWatch();
    	stopwatch.start();

//    	String elements = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//    	String elements = "electroglotospectrografie";
//    	String elements = "aaeelmnur";
    	String elements = "aelmnu";
    	System.out.println("Enter Number of character: ");
/*    	InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		String inputStr="";
		try {
			inputStr = reader.readLine();
			
		} catch (IOException e) {e.printStackTrace();}
	
		int num  = Integer.parseInt(inputStr);
    	*/
    	int num = elements.length();
    	System.out.println(num);
    	System.out.println("Enter Number of combinations: ");

	/*	try {
			inputStr = reader.readLine();
			
		} catch (IOException e) {e.printStackTrace();}
		int myCharSize  = Integer.parseInt(inputStr);
        */
		//int myCharSize = 4;
    	for (int j=3; j<elements.length()+1;j++){
    		int myCharSize = j;
//			System.out.println(myCharSize);
			
			char[] myListChar = new char[num];
	        for (int i = 0; i < num; i++) {
	            myListChar[i] = elements.charAt(i);
	        }
	        getPermutations(myListChar, myCharSize);
	        
	//        System.out.println("11!=" + (2*3*4*5*6*7*8*9*11*10));
	        
	//        System.out.println(s.contains("abracadabra"));
	//        System.out.println(s.contains("rara"));
		}
        System.out.println(s);
        System.out.println(s.size());
		
        stopwatch.stop();
        System.out.println(stopwatch);
    }

    private static String sortStringChars(String unsorted) {
    	char[] content = unsorted.toCharArray();
    	java.util.Arrays.sort(content);
    	return new String(content);
    }
    
}

