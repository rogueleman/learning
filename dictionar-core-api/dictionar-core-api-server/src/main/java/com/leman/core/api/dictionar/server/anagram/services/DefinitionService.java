package com.leman.core.api.dictionar.server.anagram.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.leman.core.api.dictionar.common.anagram.entities.DefinitionEntity;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition;
import com.leman.core.data.dictionar.jpa.repository.definition.IDefinitionRepository;
import com.leman.core.data.dictionar.jpa.spring.transaction.AnagramReadOnlyTx;

@Service("definitionService")
@AnagramReadOnlyTx
public class DefinitionService implements IDefinitionService {

	private final static Log LOG = LogFactory.getLog(DefinitionService.class);

	@Resource
	private IDefinitionRepository definitionRepository;

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
	protected DefinitionService(final IDefinitionRepository definitionRepository) {
		this.definitionRepository = definitionRepository;
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