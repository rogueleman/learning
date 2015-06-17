package com.leman.core.data.dictionar.jpa.repository.definition;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.leman.core.data.dictionar.jpa.Dictionar;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition;
import com.leman.core.data.dictionar.jpa.domain.definition.Definition_;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.emailvision.data.jpa.repository.AbstractGenericRepository;

/**
 * to be deleted
 */
@Deprecated
@Repository("definitionRepository")
public class DefinitionRepository extends AbstractGenericRepository<Definition, Integer> implements IDefinitionRepository {

	@PersistenceContext(type=PersistenceContextType.TRANSACTION, unitName=Dictionar.PERSISTENCE_UNIT_NAME)
	@Required
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	//TODO Here to define which words we take from Definition table 
	//Now we take all words starting with the value of the search
	//There are words in the table that does not appear when searching in the online page www.dexonline.ro
	public List<Definition> getDefinitions(String search) {
    	final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    	final CriteriaQuery<Definition> criteriaQuery = criteriaBuilder.createQuery(Definition.class);
    	final Root<Definition> fromDefinition = criteriaQuery.from(Definition.class);
    	
    	if (search != null) {
    		Predicate predicateSearch = criteriaBuilder.like(criteriaBuilder.lower(fromDefinition.get(Definition_.lexicon)), search.toLowerCase() + "%");
    		criteriaQuery.where(predicateSearch);
    	}
    	
    	return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(fromDefinition.get(Definition_.id))));
	}
	
}
