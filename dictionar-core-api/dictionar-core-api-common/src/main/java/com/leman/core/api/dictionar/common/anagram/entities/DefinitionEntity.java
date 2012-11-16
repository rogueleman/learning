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

@XmlType(name = DefinitionEntity.NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = DefinitionEntity.NAME)
public class DefinitionEntity {

	public static final String NAME = "definitionEntity";
	
	@XmlElement
	private String lexicon;
	
	/**
	 * Default Constructor
	 */
	public DefinitionEntity() {
		
	}
	
	/**
	 * 
	 * @param lexicon
	 */
	public DefinitionEntity(final String lexicon) {
		this.lexicon = lexicon;
	}


	/**
	 * @return the lexicon
	 */
	public String getLexicon() {
		return lexicon;
	}

	/**
	 * @param lexicon the lexicon to set
	 */
	public void setLexicon(String lexicon) {
		this.lexicon = lexicon;
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
