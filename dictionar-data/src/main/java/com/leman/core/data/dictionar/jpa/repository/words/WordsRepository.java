package com.leman.core.data.dictionar.jpa.repository.words;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.emailvision.data.jpa.repository.AbstractGenericRepository;
import com.leman.core.data.dictionar.jpa.Anagram;
import com.leman.core.data.dictionar.jpa.domain.words.Words;
import com.leman.core.data.dictionar.jpa.domain.words.Words_;

@Repository("wordsRepository")
public class WordsRepository extends AbstractGenericRepository<Words, Long> implements IWordsRepository {

	@PersistenceContext(type=PersistenceContextType.TRANSACTION, unitName=Anagram.PERSISTENCE_UNIT_NAME)
	@Required
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Words> getWords() {
    	final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    	final CriteriaQuery<Words> criteriaQuery = criteriaBuilder.createQuery(Words.class);
    	final Root<Words> fromUser = criteriaQuery.from(Words.class);
    	return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(fromUser.get(Words_.id))));
	}
	
}
