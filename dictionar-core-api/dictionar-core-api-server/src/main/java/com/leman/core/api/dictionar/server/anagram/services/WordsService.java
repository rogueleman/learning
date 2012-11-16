package com.leman.core.api.dictionar.server.anagram.services;

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
import com.leman.core.api.dictionar.common.anagram.entities.AnagramEntity;
import com.leman.core.data.dictionar.jpa.domain.words.Words;
import com.leman.core.data.dictionar.jpa.repository.words.IWordsRepository;
import com.leman.core.data.dictionar.jpa.spring.transaction.AnagramPersistTx;
import com.leman.core.data.dictionar.jpa.spring.transaction.AnagramReadOnlyTx;

@Service("wordsService")
@AnagramReadOnlyTx
public class WordsService implements IWordsService {

	private final static Log LOG = LogFactory.getLog(WordsService.class);

	@Resource
	private IWordsRepository wordsRepository;

	/**
	 * Default Constructor
	 */
	public WordsService() {
		super();
	}

	/**
	 * Only for unit test
	 * 
	 * @param wordsRepository
	 */
	protected WordsService(final IWordsRepository wordsRepository) {
		this.wordsRepository = wordsRepository;
	}

	@Override
	public AnagramEntity getAnagramEntityForRandomWord() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getAnagramEntityForRandomWord ..... ");
		}
		boolean test=false;
		
		Long id;
		
		do {
			id = getRandomWordId() + 1;
			if (LOG.isDebugEnabled()) {
				LOG.debug("generated id: " + id);
			}
			test = ! itsAGoodWord(id);
		} while (test);

		final Words word = wordsRepository.load(id);
		return convertWordToAnagramEntity(word);
	}

	@Override
	public Set<AnagramEntity> getAllAnagramListForWord(final String sortedChars, final Boolean areDiacriticsPresent) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getAllAnagramListForWord ");
		}

		final List<Words> words = wordsRepository.getWords(sortedChars, areDiacriticsPresent);
		final HashSet<AnagramEntity> anagramEntities = new HashSet<AnagramEntity>(words.size());
		for (final Words word : words) {
			anagramEntities.add(convertWordToAnagramEntity(word));
		}

		return anagramEntities;
	}
	
	@Override
	public Set<AnagramEntity> getAllAnagramListForWordAndSubWords(final String sortedChars, final Boolean areDiacriticsPresent) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getAllAnagramListForWordAndSubWords ");
		}
		
		
		
		Set<AnagramEntity> anagramEntities = new HashSet<AnagramEntity>();
		anagramEntities = getAllAnagramListForWord(sortedChars, areDiacriticsPresent);

		return anagramEntities;
	}
	
	
	private Long getRandomWordId(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering getRandomWordId ..... ");
		}
		return new Long(new Random().nextInt(wordsRepository.countAll().intValue()));
	}
	
	/**
	 * Verify if the word can be used (length greater than 3)
	 * @param id 
	 * @return
	 */
	private Boolean itsAGoodWord(Long id){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering itsAGoodWord with id: " + id);
		}
		// TODO: put the length of the word in property file
		return (wordsRepository.load(id).getWordLength() > 2) ? true : false;
	}
	
	/**
	 * 
	 * @param anagramEntity
	 * @return
	 */
	private AnagramEntity convertWordToAnagramEntity(final Words word) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering convertWordsToAnagramEntity ..... " + word.getId());
		}
		return new AnagramEntity(word.getId(), word.getLang().getLang(), word.getWord(), word.getWordWithoutDiacritics(),
				word.getSortedWordChars(), word.getSortedWordCharsWithoutDiacritics(), word.getWordLength());
	}

	
	@Override
	@AnagramPersistTx
	public AnagramEntity postWord(final String word, final Integer langId) throws IOException {	
		if (LOG.isDebugEnabled()) {
			LOG.debug("Entering postWord..... ");
		}
		
		String wordWithoutDiacritics = Normalizer.normalize(word, Normalizer.Form.NFD);
		wordWithoutDiacritics = word.replaceAll("[^\\p{ASCII}]", "");
		
		Words wordObject = new Words(Language.getByValue(langId), word, WordUtils.sortStringChars(word), wordWithoutDiacritics, WordUtils.sortStringChars(wordWithoutDiacritics));
		
		wordsRepository.persist(wordObject);
		return convertWordToAnagramEntity(wordObject);
	}	
	
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	@ImgdbPersistTx
//	public AnagramEntity postImage(final Long managerId, final Long clientId, final Long clientImageQuotaUsed, final Long clientImageMaxUsed, final String name, final String description, final InputStream uploadedInputStream, final FormDataContentDisposition fileDetail) 
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