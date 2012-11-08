package com.leman.core.data.dictionar.jpa.domain.words;

import com.leman.anagram.Language;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Words.class)
public abstract class Words_ {

	public static volatile SingularAttribute<Words, String> sortedWordCharsWithoutDiacritics;
	public static volatile SingularAttribute<Words, Long> id;
	public static volatile SingularAttribute<Words, String> wordWithoutDiacritics;
	public static volatile SingularAttribute<Words, Integer> wordLength;
	public static volatile SingularAttribute<Words, String> sortedWordChars;
	public static volatile SingularAttribute<Words, String> word;
	public static volatile SingularAttribute<Words, Language> lang;

}

