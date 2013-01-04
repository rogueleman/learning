package com.leman.core.data.dictionar.jpa.domain.word;

import com.leman.anagram.Language;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Word.class)
public abstract class Word_ {

	public static volatile SingularAttribute<Word, String> sortedWordCharsWithoutDiacritics;
	public static volatile SingularAttribute<Word, Long> id;
	public static volatile SingularAttribute<Word, String> wordWithoutDiacritics;
	public static volatile SingularAttribute<Word, Integer> wordLength;
	public static volatile SingularAttribute<Word, String> sortedWordChars;
	public static volatile SingularAttribute<Word, String> word;
	public static volatile SingularAttribute<Word, Language> lang;

}

