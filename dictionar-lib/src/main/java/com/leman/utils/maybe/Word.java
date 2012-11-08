package com.leman.utils.maybe;

import java.util.ArrayList;
import java.util.Collections;

public class Word {
	private Integer nrChars;
	private String word;
	
	Word (){
		nrChars = 0;
		word = null;
	}
	
	Word (String _word){
		nrChars = _word.length();
		word = _word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getNrChars() {
		return nrChars;
	}

	public void setNrChars(Integer nrChars) {
		this.nrChars = nrChars;
	}
	
	public ArrayList<Character> sortLettersToArrayList(){
		ArrayList<Character> sortedWord = new ArrayList<Character>();
		for(int i=0; i<nrChars; i++){
			sortedWord.add(word.charAt(i));
		}
		Collections.sort(sortedWord);
		return sortedWord;
	}
}
