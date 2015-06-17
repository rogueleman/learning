package com.leman.core.api.dictionar.server.services;

import static com.leman.anagram.WordUtils.sortStringChars;

import java.io.IOException;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import com.leman.anagram.Language;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.leman.core.data.dictionar.jpa.domain.word.Word;
import com.leman.core.data.dictionar.jpa.repository.word.IWordRepository;
import com.leman.core.data.dictionar.jpa.spring.transaction.DictionarPersistTx;
import com.leman.core.data.dictionar.jpa.spring.transaction.DictionarReadOnlyTx;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service("wordService")
@DictionarReadOnlyTx
public class WordService implements IWordService {

    private final static Log LOG = LogFactory.getLog(WordService.class);

    @Resource
    private IWordRepository wordRepository;

    /**
     * Default Constructor
     */
    public WordService() {
        super();
    }

    /**
     * Only for unit test
     *
     * @param wordRepository
     */
    protected WordService(final IWordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public WordEntity getWordEntityForRandomWord() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering getWordEntityForRandomWord ..... ");
        }
        boolean test;

        Long id;
        do {
            id = getRandomWordId() + 1;
            test = !itsAGoodWord(id);
        } while (test);

        final Word word = wordRepository.load(id);
        return convertWordToWordEntity(word);
    }

    @Override
    public Set<WordEntity> getAnagramListForWord(final String sortedChars, final Boolean areDiacriticsPresent) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering getAnagramListForWord ");
        }

        final List<Word> wordList = wordRepository.getWords(sortedChars, areDiacriticsPresent);
        final HashSet<WordEntity> wordEntities = new HashSet<>(wordList.size());
        for (final Word word : wordList) {
            wordEntities.add(convertWordToWordEntity(word));
        }

        return wordEntities;
    }

    @Override
    public Set<WordEntity> getSubAnagramListForWord(List<String> sortedChars, Boolean areDiacriticsPresent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering getSubAnagramListForWord ");
        }

        final List<Word> wordList = wordRepository.getWordsAndSubWords(sortedChars, areDiacriticsPresent);
        final HashSet<WordEntity> wordEntities = new HashSet<>(wordList.size());
        for (final Word word : wordList) {
            wordEntities.add(convertWordToWordEntity(word));
        }

        return wordEntities;
    }

    private Long getRandomWordId() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering getRandomWordId ..... ");
        }
        return new Long(new Random().nextInt(wordRepository.getIdOfLastElementFromTable(Language.ro).intValue()));
    }

    /**
     * Verify if the word can be used (length greater than 3)
     *
     * @param id
     * @return
     */
    private Boolean itsAGoodWord(final Long id) {
        // TODO: merge refactorizata sa intoarca direct Word sau null; asfel nu o sa mai fiu nevoit sa fac un load
        // dupa aceasta metoda.
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering itsAGoodWord with id: " + id);
        }
        // TODO: put the length of the word in property file
        final Word word = wordRepository.findById(id);
        if (word == null) {
            return false;
        }
        return (word.getWordLength() > 2) ? true : false;
    }

    /**
     * @param word
     * @return WordEntity
     */
    private WordEntity convertWordToWordEntity(final Word word) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering convertWordToWordEntity ..... " + word.getId());
        }
        return new WordEntity(word.getId(),
                              word.getLang().getLang(),
                              word.getWord(),
                              word.getWordWithoutDiacritics(),
                              word.getSortedWordChars(),
                              word.getSortedWordCharsWithoutDiacritics(),
                              word.getWordLength());
    }

    @Override
    @DictionarPersistTx
    public WordEntity postWord(final String word, final Integer langId) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering postWord..... ");
        }

        // TODO see which method to use for normalize
        String wordWithoutDiacritics = Normalizer.normalize(word, Normalizer.Form.NFD);
        wordWithoutDiacritics = word.replaceAll("[^\\p{ASCII}]", "");

        Word wordObject = new Word(Language.getByValue(langId),
                                   word,
                                   sortStringChars(word),
                                   wordWithoutDiacritics,
                                   sortStringChars(wordWithoutDiacritics));

        wordRepository.persist(wordObject);
        return convertWordToWordEntity(wordObject);
    }

    //	@ImgdbDefaultTx
    //	@Override
    //	public void deleteImage(final List<Long> managerIds,  final Long clientId, final Long imageId) {
    //		if (LOG.isDebugEnabled()) {
    //			LOG.debug("Entering deleteImage");
    //		}
    //		isNegativeAndZeroThrowIllegalArgumentException(clientId, format(ERROR_MISSING_PARAMETER, "clientId"));
    //
    //		final Image image = imageRepository.findByIdAndManagerId(managerIds, imageId);
    //
    //		final String ftpImageName = image.getUrl().substring(image.getUrl().lastIndexOf("/"));
    //		final String ftpImageNameThumb = image.getUrlThumb().substring(image.getUrlThumb().lastIndexOf("/"));
    //
    //		final ArrayList<FTPFile> ftpFiles = new ArrayList<FTPFile>();
    //		ftpFiles.add(new FTPFile(ftpImageName));
    //		ftpFiles.add(new FTPFile(ftpImageNameThumb));
    //
    //		FTPUpload.deleteFiles(ftpServer, ftpPort, ftpLogin, ftpPassword, ftpFiles, String.valueOf(clientId));
    //
    //		imageRepository.delete(image);
    //	}

}