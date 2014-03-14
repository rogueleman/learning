package com.leman.core.api.dictionar.server.services;

import java.io.IOException;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.leman.anagram.Language;
import com.leman.anagram.WordUtils;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.leman.core.data.dictionar.jpa.domain.word.Word;
import com.leman.core.data.dictionar.jpa.repository.word.IWordRepository;
import com.leman.core.data.dictionar.jpa.spring.transaction.DictionarPersistTx;
import com.leman.core.data.dictionar.jpa.spring.transaction.DictionarReadOnlyTx;

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
		boolean test=false;
		
		Long id;
		do {
			id = getRandomWordId() + 1;
			test = ! itsAGoodWord(id);
		} while (test);

		final Word word = wordRepository.load(id);
		return convertWordToWordEntity(word);
	}

	@Override
	public Set<WordEntity> getAllAnagramListForWord(final String sortedChars, final Boolean areDiacriticsPresent) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getAllAnagramListForWord ");
		}

		final List<Word> wordList = wordRepository.getWords(sortedChars, areDiacriticsPresent);
		final HashSet<WordEntity> anagramEntities = new HashSet<WordEntity>(wordList.size());
		for (final Word word : wordList) {
			anagramEntities.add(convertWordToWordEntity(word));
		}

		return anagramEntities;
	}
	
	@Override
	public Set<WordEntity> getAllAnagramListForWordAndSubWords(final String sortedChars, final Boolean areDiacriticsPresent) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getAllAnagramListForWordAndSubWords ");
		}
		
		
		
		Set<WordEntity> anagramEntities = new HashSet<WordEntity>();
		anagramEntities = getAllAnagramListForWord(sortedChars, areDiacriticsPresent);

		return anagramEntities;
	}
	
	
	private Long getRandomWordId(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getRandomWordId ..... ");
		}
		return new Long(new Random().nextInt(wordRepository.getIdOfLastElementFromTable(Language.ro).intValue()));
	}
	
	/**
	 * Verify if the word can be used (length greater than 3)
	 * @param id 
	 * @return
	 */
	private Boolean itsAGoodWord(final Long id){
	// TODO: merge refactorizata sa intoarca direct Word sau null; asfel nu o sa mai fiu nevoit sa fac un load dupa aceasta metoda.
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering itsAGoodWord with id: " + id);
		}
		// TODO: put the length of the word in property file
		final Word word = wordRepository.findById(id);
		if (word == null){
			return false;
		}
		return (word.getWordLength() > 2) ? true : false;
	}
	
	/**
	 * 
	 * @param word
	 * @return WordEntity
	 */
	private WordEntity convertWordToWordEntity(final Word word) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering convertWordToWordEntity ..... " + word.getId());
		}
		return new WordEntity(word.getId(), word.getLang().getLang(), word.getWord(), word.getWordWithoutDiacritics(),
				word.getSortedWordChars(), word.getSortedWordCharsWithoutDiacritics(), word.getWordLength());
	}

	
	@Override
	@DictionarPersistTx
	public WordEntity postWord(final String word, final Integer langId) throws IOException {	
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering postWord..... ");
		}
		
		String wordWithoutDiacritics = Normalizer.normalize(word, Normalizer.Form.NFD);
		wordWithoutDiacritics = word.replaceAll("[^\\p{ASCII}]", "");
		
		Word wordObject = new Word(Language.getByValue(langId), word, WordUtils.sortStringChars(word), wordWithoutDiacritics, WordUtils.sortStringChars(wordWithoutDiacritics));
		
		wordRepository.persist(wordObject);
		return convertWordToWordEntity(wordObject);
	}	
	
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	@ImgdbPersistTx
//	public WordEntity postImage(final Long managerId, final Long clientId, final Long clientImageQuotaUsed, final Long clientImageMaxUsed, final String name, final String description, final InputStream uploadedInputStream, final FormDataContentDisposition fileDetail) 
//			throws IOException {	
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("Entering postImage..... ");
//		}
//		
//		isNegativeAndZeroThrowIllegalArgumentException(clientId, format(ERROR_MISSING_PARAMETER, "clientId"));
//		isNegativeAndZeroThrowIllegalArgumentException(managerId, format(ERROR_MISSING_PARAMETER, "managerId"));
//		
//		
//		final String fileName = (fileDetail != null) ? fileDetail.getFileName() : null;
//		if (uploadedInputStream == null && fileName == null) {
//			throw new IllegalArgumentException("The image is missing");
//		
//		}
//
//		final String extension = FilenameUtils.getExtension(fileName);
//		final boolean extensionChecked = extensions.contains(extension != null ? extension.toLowerCase() : StringUtils.EMPTY);
//		if (! extensionChecked) {
//			throwWebApplicationException(BAD_REQUEST.getStatusCode(), ERROR_IMG_INVALID);
//		}
//
//		final Image imageEntity = new Image(managerId, name, (description == null)?"":description);
//
//		final File image = new File(imageLocalTempPath + File.separator + fileName);
//		final BufferedImage bi = getImageInfo(image, uploadedInputStream, clientImageQuotaUsed, clientImageMaxUsed, imageEntity);
//		
//		File imageThumbnail = null;
//		try {
//			final ArrayList<FTPFile> ftpFiles = new ArrayList<FTPFile>();
//
//			// Generate URL for image
//			final Integer generateImageName = generateId();
//			final String ftpFileName = generateImageName + "." + extension;
//			final String urlImage = imageBaseurl + FileNameUtils.buildRelativePathById(clientId) +  File.separator +  ftpFileName;
//			imageEntity.setUrl(urlImage);
//			imageEntity.setUrlThumb(urlImage);
//			ftpFiles.add(new FTPFile(ftpFileName, image));
//
//			// Generate Thumbnail
//			imageThumbnail = new File(imageLocalTempPath + File.separator + "t_" + fileName);
//			final BufferedImage thumbnail = Thumbnail.createThumbnailFromBufferedImage(bi);
//
//			if (thumbnail != null ) {
//				// Thumbnail needs to be uploaded
//				ImageIO.write(thumbnail, "PNG", imageThumbnail);
//				imageEntity.setUrlThumb(imageBaseurl + FileNameUtils.buildRelativePathById(clientId) +  File.separator + generateImageName + thumbnailExtension);
//				ftpFiles.add(new FTPFile(generateImageName + thumbnailExtension, imageThumbnail));
//			}
//
//			final boolean uploadedFiles = FTPUpload.uploadFile(ftpServer, ftpPort, ftpLogin, ftpPassword, ftpFiles , String.valueOf(clientId));
//
//			if (uploadedFiles) {
//				if (LOG.isDebugEnabled()) {
//					LOG.debug("files are uploaded on ftp... trying to write in database the object: " + imageEntity.getName() + imageEntity.getDescription());
//				}
//				imageRepository.persist(imageEntity);
//				return convertImageToImageEntity(imageEntity);
//			} else {
//				throwWebApplicationException(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ERROR_FTP_ERROR);
//				return null;
//			}
//		} catch (final IOException ioe) {
//			LOG.error("Error to write thumbnail image in temporary folder", ioe);
//			throw ioe;
//		} finally {
//			if (image != null) {
//				image.delete();
//			}
//			if (imageThumbnail != null) {
//				imageThumbnail.delete();
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param image
//	 * @param uploadedInputStream
//	 * @param imageEntity
//	 */
//	private BufferedImage getImageInfo(final File image, final InputStream uploadedInputStream, final Long clientImageQuotaUsed, final Long clientImageMaxUsed, final Image imageEntity) throws IOException {
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("Entering getImageInfo ");
//		}
//		final Long maxFileSize = 1024 * maxfilesize;
//		try {
//			FileUtils.copyInputStreamToFile(uploadedInputStream, image);
//			final BufferedImage bi = ImageIO.read(image);
//			if (null == bi) {
//				throwWebApplicationException(BAD_REQUEST.getStatusCode(), ERROR_IMG_CONTENT_TYPE);
//			}
//
//			final Integer imageSize = (int) image.length();
//			if (maxFileSize < imageSize) {
//				throwWebApplicationException(BAD_REQUEST.getStatusCode(), ERROR_IMG_LENGHT);
//			}
//
//			if (LOG.isDebugEnabled()) {
//				LOG.debug(format("clientImageQuotaUsed:\"{0}\", imageSize:\"{1}\", clientImageMaxUsed:\"{2}\"", clientImageQuotaUsed, imageSize, clientImageMaxUsed));
//			}
//			if (clientImageQuotaUsed + imageSize >= clientImageMaxUsed) {
//				// 402 => Payment Required
//				throwWebApplicationException(402, ERROR_CLIENT_QUOTA_MAX);
//			}
//
//			imageEntity.setHeight(bi.getHeight());
//			imageEntity.setWidth(bi.getWidth());
//			imageEntity.setSize((int)image.length());
//
//			return bi;
//		} catch (final IOException ioe) {
//			LOG.error("Error to write image in temporary folder", ioe);
//			throw ioe;
//		}
//	}
//
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
//
//	@Override
//	@ImgdbDefaultTx
//	public void putImage(final List<Long> managerIds, final Long imageId, final String name, final String description) {
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("Entering putImage");
//		}
//		final Image image = imageRepository.findByIdAndManagerId(managerIds, imageId);
//		if (StringUtils.isNotBlank(name)) {
//			image.setName(name);
//		}
//		if (StringUtils.isNotBlank(description)) {
//			image.setDescription(description);
//		}
//	}
//
//	private void throwWebApplicationException(final int status,final MediaErrorType errorType) {
//		throw new WebApplicationException(Response.status(status).entity(new ExceptionEntity(errorType.getErrorCode(), errorType.getErrorMessage())).build());
//	}
//	
//	/**
//	 * Generate an image name, which is in fact a random number
//	 *
//	 * @return a random number
//	 */
//	private Integer generateId() {
//		try {
//			final SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
//			// generate a random number
//			final Integer randomNum = Integer.valueOf(prng.nextInt());
//			return Math.abs(randomNum);
//		} catch (final NoSuchAlgorithmException e) {
//			LOG.error("Cannot generate random id", e);
//			throwWebApplicationException(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ERROR_IMG_GENERATE_ID);
//			return null;
//		}
//	}
}