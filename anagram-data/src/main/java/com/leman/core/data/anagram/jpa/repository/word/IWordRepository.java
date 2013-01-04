package com.leman.core.data.anagram.jpa.repository.word;

import java.util.List;

import com.emailvision.data.jpa.repository.IGenericRepository;
import com.leman.core.data.anagram.jpa.domain.word.Word;

public interface IWordRepository extends IGenericRepository<Word, Long> {

	List<Word> getWords();

	List<Word> getWords(String sortedChars, Boolean areDiacriticsPresent);

}
