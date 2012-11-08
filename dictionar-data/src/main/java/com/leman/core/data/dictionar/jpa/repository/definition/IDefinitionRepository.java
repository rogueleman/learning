package com.leman.core.data.dictionar.jpa.repository.definition;

import java.util.List;

import com.emailvision.data.jpa.repository.IGenericRepository;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition;

public interface IDefinitionRepository extends IGenericRepository<Definition, Integer> {

	List<Definition> getDefinitions(String search);
	
}
