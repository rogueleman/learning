package com.leman.core.api.dictionar.common.anagram.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlType(name = AnagramEntity.NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = AnagramEntity.NAME)
public class AnagramEntity {

	public static final String NAME = "anagramEntity";
	
	@XmlElement
	private Long id;
	
	@XmlElement
	private String lang;
	
	@XmlElement
	private String sortedWordChars;
	
	@XmlElement
	private String sortedWordCharsWithoutDiacritics;
	
	@XmlElement
	private String word;

	@XmlElement
	private String wordWithoutDiacritics;

	@XmlElement
	private Integer wordLength;

	/**
	 * Default Constructor
	 */
	public AnagramEntity() {
		
	}
	
	/**
	 * 
	 * @param imageId
	 * @param name
	 * @param description
	 * @param url
	 * @param urlThumb
	 * @param size
	 * @param height
	 * @param width
	 * @param dateCreation
	 * @param dateModification
	 * @param dateLastUse
	 * @param managerId
	 */
	public AnagramEntity(final Long id, final String lang, final String word, final String wordWithoutDiacritics, final String sortedWordChars, final String sortedWordCharsWithoutDiacritics, final Integer wordLength) {
		this.id = id;
		this.lang = lang;
		this.sortedWordChars = sortedWordChars;
		this.sortedWordCharsWithoutDiacritics = sortedWordCharsWithoutDiacritics;
		this.word = word;
		this.wordWithoutDiacritics = wordWithoutDiacritics;
		this.wordLength = wordLength;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the sortedWordChars
	 */
	public String getSortedWordChars() {
		return sortedWordChars;
	}

	/**
	 * @param sortedWordChars the sortedWordChars to set
	 */
	public void setSortedWordChars(String sortedWordChars) {
		this.sortedWordChars = sortedWordChars;
	}

	/**
	 * @return the sortedWordCharsWithoutDiacritics
	 */
	public String getSortedWordCharsWithoutDiacritics() {
		return sortedWordCharsWithoutDiacritics;
	}

	/**
	 * @param sortedWordCharsWithoutDiacritics the sortedWordCharsWithoutDiacritics to set
	 */
	public void setSortedWordCharsWithoutDiacritics(
			String sortedWordCharsWithoutDiacritics) {
		this.sortedWordCharsWithoutDiacritics = sortedWordCharsWithoutDiacritics;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the wordWithoutDiacritics
	 */
	public String getWordWithoutDiacritics() {
		return wordWithoutDiacritics;
	}

	/**
	 * @param wordWithoutDiacritics the wordWithoutDiacritics to set
	 */
	public void setWordWithoutDiacritics(String wordWithoutDiacritics) {
		this.wordWithoutDiacritics = wordWithoutDiacritics;
	}

	/**
	 * @return the wordLength
	 */
	public Integer getWordLength() {
		return wordLength;
	}

	/**
	 * @param wordLength the wordLength to set
	 */
	public void setWordLength(Integer wordLength) {
		this.wordLength = wordLength;
	}

	@Override
	public String toString() {
	   return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	 
	@Override
	public int hashCode() {
	   return HashCodeBuilder.reflectionHashCode(this);
	}
	 
	@Override
	public boolean equals(final Object other) {
	    return EqualsBuilder.reflectionEquals(this, other);
	}
}
