package com.leman.core.data.dictionar.jpa.repository.word;

import java.util.List;
import java.util.Set;

import com.leman.anagram.Language;
import com.leman.core.data.dictionar.jpa.domain.word.Word;

import com.emailvision.data.jpa.repository.IGenericRepository;

public interface IWordRepository extends IGenericRepository<Word, Long> {

	List<Word> getWords();

	List<Word> getWords(String sortedChars, Boolean areDiacriticsPresent);

    List<Word> getWordsAndSubWords(Set<String> sortedChars, Boolean areDiacriticsPresent);

	Word findByWord(String word, Boolean areDiacriticsPresent);

	Long getIdOfLastElementFromTable(Language language);

}
