package com.leman.core.data.dictionar.jpa.repository.definition;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.emailvision.data.jpa.repository.AbstractGenericRepository;
import com.leman.core.data.dictionar.jpa.Anagram;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition_;

@Repository("definitionRepository")
public class DefinitionRepository extends AbstractGenericRepository<Definition, Integer> implements IDefinitionRepository {

	@PersistenceContext(type=PersistenceContextType.TRANSACTION, unitName=Anagram.PERSISTENCE_UNIT_NAME)
	@Required
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Definition> getDefinitions(String search) {
    	final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    	final CriteriaQuery<Definition> criteriaQuery = criteriaBuilder.createQuery(Definition.class);
    	final Root<Definition> fromDefinition = criteriaQuery.from(Definition.class);
    	
    	if (search != null) {
    		Predicate predicateSearch = criteriaBuilder.like(criteriaBuilder.lower(fromDefinition.get(Definition_.lexicon)), "%" + search.toLowerCase() + "%");
    		criteriaQuery.where(predicateSearch);
    	}
    	
    	return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(fromDefinition.get(Definition_.id))));
	}
	
}
