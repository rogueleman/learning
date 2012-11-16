package com.leman.core.api.dictionar.server.anagram.services;

import java.util.Set;

import com.leman.core.api.dictionar.common.anagram.entities.DefinitionEntity;

public interface IDefinitionService {

	Set<DefinitionEntity> getDefinitionListWithBeginingChars(String search);

}
