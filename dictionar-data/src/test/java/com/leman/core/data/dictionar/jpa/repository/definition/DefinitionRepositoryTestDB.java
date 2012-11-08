package com.leman.core.data.dictionar.jpa.repository.definition;

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

import com.leman.core.data.dictionar.jpa.domain.definition.Definition;
import com.leman.core.data.dictionar.spring.config.PersistenceJPAConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class DefinitionRepositoryTestDB {

	private static final String NIMIC = "nimic";
	private static final String LEXICAL = "lexical";
	private static final String LEXIC = "lexic";
	private static final String LEXICON = "lexicon";
	@Resource(name="definitionRepository")
	private IDefinitionRepository definitionRepository;
	
	@Test
	public void should_test_valid_conf() throws Exception {
		
	}
	
	@Test
	public void should_get_definitions_full_list() {
		createDefinitionList();
		
		List<Definition> definitionList = definitionRepository.getDefinitions(null);

		assertThat(definitionList.size()).isEqualTo(4);
		assertThat(definitionList.get(0).getLexicon().toLowerCase()).isEqualTo(LEXICON);
		assertThat(definitionList.get(1).getLexicon().toLowerCase()).isEqualTo(LEXIC);
		assertThat(definitionList.get(2).getLexicon().toLowerCase()).isEqualTo(LEXICAL);
		assertThat(definitionList.get(3).getLexicon().toLowerCase()).isEqualTo(NIMIC);
	}	

	@Test
	public void should_get_definitions_starting_with_something() {
		createDefinitionList();
		
		List<Definition> definitionList = definitionRepository.getDefinitions(LEXIC);

		assertThat(definitionList.size()).isEqualTo(3);
		assertThat(definitionList.get(0).getLexicon().toLowerCase()).isEqualTo(LEXICON);
		assertThat(definitionList.get(1).getLexicon().toLowerCase()).isEqualTo(LEXIC);
		assertThat(definitionList.get(2).getLexicon().toLowerCase()).isEqualTo(LEXICAL);
	}	
	
	private void createDefinitionList(){
		Definition definition1 = new Definition(100000000,LEXICON);
		definitionRepository.persist(definition1);
		Definition definition2 = new Definition(200000000,LEXIC);
		definitionRepository.persist(definition2);
		Definition definition3 = new Definition(300000000,LEXICAL);
		definitionRepository.persist(definition3);
		Definition definition4 = new Definition(400000000,NIMIC);
		definitionRepository.persist(definition4);
	}
}
