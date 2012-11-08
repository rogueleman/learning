package com.leman.core.data.dictionar.jpa.repository.words;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.leman.anagram.Language;
import com.leman.core.data.dictionar.jpa.domain.words.Words;
import com.leman.core.data.dictionar.spring.config.PersistenceJPAConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class WordsRepositoryTestDB {

	private static final String LEXICON = "lexicon";
	@Resource(name="wordsRepository")
	private IWordsRepository wordsRepository;
	
	@Test
	public void should_test_valid_conf() throws Exception {
		
	}
	
	@Test
	public void should_get_definitions_full_list() {
		createDefinitionList();
		
		List<Words> definitionList = wordsRepository.getWords();

		assertThat(definitionList.size()).isEqualTo(1);
		assertThat(definitionList.get(0).getWord().toLowerCase()).isEqualTo(LEXICON);
		assertThat(definitionList.get(0).getWordLength()).isEqualTo(LEXICON.length());
		
//		assertThat(definitionList.get(1).getLexicon().toLowerCase()).isEqualTo(LEXIC);
//		assertThat(definitionList.get(2).getLexicon().toLowerCase()).isEqualTo(LEXICAL);
//		assertThat(definitionList.get(3).getLexicon().toLowerCase()).isEqualTo(NIMIC);
	}	

	private void createDefinitionList(){
		Words word1 = new Words(Language.ro, LEXICON, null, null, null);
		wordsRepository.persist(word1);
	}
}
