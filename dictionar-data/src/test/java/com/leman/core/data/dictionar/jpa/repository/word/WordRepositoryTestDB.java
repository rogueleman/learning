package com.leman.core.data.dictionar.jpa.repository.word;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.annotation.Resource;

import com.leman.anagram.Language;
import com.leman.core.data.dictionar.jpa.domain.word.Word;
import com.leman.core.data.dictionar.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class WordRepositoryTestDB {

	private static final String LEXICON = "lexicon";
	@Resource(name="wordRepository")
	private IWordRepository wordRepository;
	
	@Test
	public void should_test_valid_conf() throws Exception {
		
	}
	
	@Test
	public void should_get_definitions_full_list() {
		createDefinitionList();
		
		List<Word> definitionList = wordRepository.getWords();

		assertThat(definitionList.size()).isEqualTo(2);
		assertThat(definitionList.get(0).getWord().toLowerCase()).isEqualTo(LEXICON);
		assertThat(definitionList.get(0).getWordLength()).isEqualTo(LEXICON.length());
		
//		assertThat(definitionList.get(1).getLexicon().toLowerCase()).isEqualTo(LEXIC);
//		assertThat(definitionList.get(2).getLexicon().toLowerCase()).isEqualTo(LEXICAL);
//		assertThat(definitionList.get(3).getLexicon().toLowerCase()).isEqualTo(NIMIC);
	}	

	private void createDefinitionList(){
		Word word1 = new Word(Language.ro, LEXICON, null, null, null);
		wordRepository.persist(word1);
		wordRepository.persist(new Word(Language.ro, LEXICON+LEXICON, null, null, null));
	}
	
	@Test
	public void should_test_getIdOfLastElementFromTable() {
		createDefinitionList();
		
		Long definitionList = wordRepository.getIdOfLastElementFromTable(Language.ro);

		assertThat(definitionList).isEqualTo(4);
	}
}
