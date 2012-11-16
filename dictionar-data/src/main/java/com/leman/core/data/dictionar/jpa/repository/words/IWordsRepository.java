package com.leman.core.data.dictionar.jpa.repository.words;

import java.util.List;

import com.emailvision.data.jpa.repository.IGenericRepository;
import com.leman.core.data.dictionar.jpa.domain.words.Words;

public interface IWordsRepository extends IGenericRepository<Words, Long> {

	List<Words> getWords();

	List<Words> getWords(String sortedChars, Boolean areDiacriticsPresent);

}
