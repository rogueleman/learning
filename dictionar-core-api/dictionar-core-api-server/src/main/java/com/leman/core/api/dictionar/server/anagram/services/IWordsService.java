package com.leman.core.api.dictionar.server.anagram.services;

import java.util.Set;

import com.leman.core.api.dictionar.common.anagram.entities.AnagramEntity;

public interface IWordsService {

	AnagramEntity getAnagramEntityForRandomWord();

	Set<AnagramEntity> getAllAnagramListForWord(String sortedChars, Boolean areDiacriticsPresent);

	Set<AnagramEntity> getAllAnagramListForWordAndSubWords(String sortedChars, Boolean areDiacriticsPresent);

//	/**
//	 * Allows to get archives with parameters
//	 * By default we get the page 1 and the number of item per page is 10
//	 * 
//	 * @param search  Allows to add some criterias
//	 * @param order   Add a column order
//	 * @param sort    ASC or DESC
//	 * @param managerId List of manager Id
//	 * @return        A list of ImageMetaEntity
//	 */
//	public List<AnagramEntity> getArchives(String search, String order, Sort sort, List<Long> managerId);
//
//	
//	/**
//	 * Allows to get archives with parameters
//	 * 
//	 * @param currentPage   Add the page number needed
//	 * @param search        Allows to add some criterias
//	 * @param order         Add a column order
//	 * @param sort          ASC or DESC
//	 * @param managerId List of manager Id
//	 * @return              A list of ImageMetaEntity
//	 */
//	public List<AnagramEntity> getArchives(Integer currentPage, String search, String order, Sort sort, List<Long> managerId);
//
//
//	/**
//	 * Allows to get archives with parameters
//	 * 
//	 * @param currentPage   Add the page number needed
//	 * @param nbItemPerPage Defines the number of item we want
//	 * @param search        Allows to add some criterias
//	 * @param order         Add a column order
//	 * @param sort          ASC or DESC
//	 * @param managerId List of manager Id
//	 * @return              A list of ImageMetaEntity
//	 */
//	public List<AnagramEntity> getArchives(Integer currentPage, Integer nbItemPerPage, String search, String order, Sort sort, List<Long> managerId);
//
//
//	/**
//	 * Retrieve Image
//	 * 
//	 * @param managerId
//	 * @param imageId
//	 * @return
//	 */
//	public AnagramEntity getImage(final List<Long> managerId, Long imageId);
//	
//	/**
//	 * Add Image
//	 * 
//	 * @param managerId
//	 * @param clientId
//	 * @param clientImageQuotaUsed 
//	 * @param name
//	 * @param description
//	 * @param uploadedInputStream
//	 * @param fileDetail
//	 * @return
//	 * @throws IOException
//	 */
//	public AnagramEntity postImage(final Long managerId,  final Long clientId, final Long clientImageQuotaUsed, final Long clientImageMaxUsed, final String name, final String description, final InputStream uploadedInputStream, final FormDataContentDisposition fileDetail) throws IOException;
//
//
//	/**
//	 * 
//	 * @param managerId
//	 * @param clientId
//	 * @param imageId
//	 */
//	public void deleteImage(final List<Long> managerId, final Long clientId, final Long imageId);
//	
//	/**
//	 * Update image name and image description
//	 * 
//	 * @param managerId
//	 * @param imageId
//	 * @param name
//	 * @param description
//	 */
//	public void putImage(final List<Long> managerId, final Long imageId, final String name, final String description);
}
