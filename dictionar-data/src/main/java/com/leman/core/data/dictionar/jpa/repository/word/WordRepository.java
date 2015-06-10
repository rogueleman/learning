package com.leman.core.data.dictionar.jpa.repository.word;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.leman.anagram.Language;
import com.leman.core.data.dictionar.jpa.Dictionar;
import com.leman.core.data.dictionar.jpa.domain.word.Word;
import com.leman.core.data.dictionar.jpa.domain.word.Word_;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.emailvision.data.jpa.repository.AbstractGenericRepository;

@Repository("wordRepository")
public class WordRepository extends AbstractGenericRepository<Word, Long> implements IWordRepository {

	@PersistenceContext(type=PersistenceContextType.TRANSACTION, unitName=Dictionar.PERSISTENCE_UNIT_NAME)
	@Required
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Word> getWords() {
    	final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    	final CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
    	final Root<Word> fromWord = criteriaQuery.from(Word.class);
    	return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(fromWord.get(Word_.id))));
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

    @Override
    public List<Word> getWordsAndSubWords(final List<String> sortedChars, final Boolean areDiacriticsPresent) {
        final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        final CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
        final Root<Word> fromWord = criteriaQuery.from(Word.class);

        SingularAttribute<Word, String> sortedCharsColumn = Word_.sortedWordCharsWithoutDiacritics;

        if (areDiacriticsPresent) {
            sortedCharsColumn = Word_.sortedWordChars;
        }

        final Expression<String> exp = fromWord.get(sortedCharsColumn);
        final Predicate predicate = exp.in(sortedChars);

        criteriaQuery.where(predicate);

        return findByCriteriaQuery(criteriaQuery.orderBy(criteriaBuilder.asc(exp)));
    }


    @Override
	public Word findByWord(final String word, final Boolean areDiacriticsPresent) {
		final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		final CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
		final Root<Word> fromWord = criteriaQuery.from(Word.class);
		
		final ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		if (areDiacriticsPresent) {
			predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(fromWord.get(Word_.word)), criteriaBuilder.upper(criteriaBuilder.literal(word))));
		}else{
			predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(fromWord.get(Word_.wordWithoutDiacritics)), criteriaBuilder.upper(criteriaBuilder.literal(word))));
		}

		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return findUniqueByCriteriaQuery(criteriaQuery);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long getIdOfLastElementFromTable (Language language) {
		// TODO: Why is not working http://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Basic_JPA_Development/Querying/Criteria 
		
		final CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		// Query for a single data element.
		final CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
		final Root fromWord = criteriaQuery.from(Word.class);
		criteriaQuery.select(criteriaBuilder.max(fromWord.get(Word_.id)));
		final Query query = entityManager.createQuery(criteriaQuery);
		
		final ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(fromWord.get(Word_.lang), language.getValue()));
		
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return (Long) query.getSingleResult();
	}
	
}
