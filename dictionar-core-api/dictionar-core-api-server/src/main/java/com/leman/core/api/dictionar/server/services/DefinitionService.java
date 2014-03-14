package com.leman.core.api.dictionar.server.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.leman.anagram.Language;
import com.leman.anagram.WordUtils;
import com.leman.core.api.dictionar.common.anagram.entities.DefinitionEntity;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition;
import com.leman.core.data.dictionar.jpa.domain.word.Word;
import com.leman.core.data.dictionar.jpa.repository.definition.IDefinitionRepository;
import com.leman.core.data.dictionar.jpa.repository.word.IWordRepository;
import com.leman.core.data.dictionar.jpa.spring.transaction.DictionarReadOnlyTx;

@Service("definitionService")
@DictionarReadOnlyTx
public class DefinitionService implements IDefinitionService {

	private final static Log LOG = LogFactory.getLog(DefinitionService.class);

	@Resource
	private IDefinitionRepository definitionRepository;
	
	@Resource
	private IWordRepository wordRepository;

	/**
	 * Default Constructor
	 */
	public DefinitionService() {
		super();
	}

	/**
	 * Only for unit test
	 * 
	 * @param definitionRepository
	 */
	protected DefinitionService(final IDefinitionRepository definitionRepository, final IWordRepository wordRepository) {
		this.definitionRepository = definitionRepository;
		this.wordRepository = wordRepository;
	}

	@Override
	public Set<DefinitionEntity> getDefinitionListWithBeginingChars(String search) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getDefinitionListWithBeginingChars ..... ");
		}

		final List<Definition> definitionList = definitionRepository.getDefinitions(search);
		
		final Set<DefinitionEntity> definitionEntities = new HashSet<DefinitionEntity>();
		for (final Definition definition : definitionList) {
			definitionEntities.add(convertDefinitionToDefinitionEntity(definition));
			String lexicon = definition.getLexicon();
			String lexiconWithoutDiacritics = Normalizer.normalize(lexicon, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			
			Word findByWord = wordRepository.findByWord(lexicon, false);
			if (findByWord == null) {
				Word word = new Word(Language.ro, lexicon, WordUtils.sortStringChars(lexicon), lexiconWithoutDiacritics, WordUtils.sortStringChars(lexiconWithoutDiacritics));
				if (LOG.isDebugEnabled()) {
					LOG.debug("word... " + word.toString());
				}
				wordRepository.persist(word);
			}
		}

		return definitionEntities;
	}
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	private DefinitionEntity convertDefinitionToDefinitionEntity(final Definition definition) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering convertDefinitionToDefinitionEntity ..... " + definition.getId() + " " + definition.getLexicon());
		}
		return new DefinitionEntity(definition.getLexicon());
	}

}