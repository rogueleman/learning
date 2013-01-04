package com.leman.core.data.anagram.jpa.repository.word;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.emailvision.data.jpa.repository.AbstractGenericRepository;
import com.leman.core.data.anagram.jpa.Anagram;
import com.leman.core.data.anagram.jpa.domain.word.Word;
import com.leman.core.data.anagram.jpa.domain.word.Word_;

@Repository("wordRepository")
public class WordRepository extends AbstractGenericRepository<Word, Long> implements IWordRepository {

	@PersistenceContext(type=PersistenceContextType.TRANSACTION, unitName=Anagram.PERSISTENCE_UNIT_NAME)
	@Required
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Word> getWords() {
    	final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    	final CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
    	final Root<Word> fromUser = criteriaQuery.from(Word.class);
    	return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(fromUser.get(Word_.id))));
	}

	
	@Override
	public List<Word> getWords(final String sortedChars, final Boolean areDiacriticsPresent) {
    	final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    	final CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
    	final Root<Word> fromWord = criteriaQuery.from(Word.class);
    	
    	SingularAttribute<Word, String> sortedCharsColumn = Word_.sortedWordCharsWithoutDiacritics;
    	
    	if (areDiacriticsPresent) {
			sortedCharsColumn = Word_.sortedWordChars;
    	}
    	
    	criteriaQuery.where(criteriaBuilder.equal(criteriaBuilder.lower(fromWord.get(sortedCharsColumn)), sortedChars));
			
    	return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(fromWord.get(sortedCharsColumn))));
	}

	
}
