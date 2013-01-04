package com.leman.core.data.dictionar.jpa.repository.word;

import java.util.List;

import com.emailvision.data.jpa.repository.IGenericRepository;
import com.leman.anagram.Language;
import com.leman.core.data.dictionar.jpa.domain.word.Word;

public interface IWordRepository extends IGenericRepository<Word, Long> {

	List<Word> getWords();

	List<Word> getWords(String sortedChars, Boolean areDiacriticsPresent);

	Word findByWord(String word, Boolean areDiacriticsPresent);

	Long getIdOfLastElementFromTable(Language language);

}
