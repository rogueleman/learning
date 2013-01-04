package com.leman.core.api.dictionar.client.anagram;

import static com.emailvision.commons.api.restful.utils.GenericResourcePath.ERROR_MISSING_PARAMETER;
import static com.emailvision.commons.http.utils.ParamChecker.isBlankThrowIllegalArgumentException;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_ARE_DIACRITICS_PRESENTS;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_SORTED_CHARS;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.RANDOM_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORDS_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORD_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_DEFINITION_SEARCH;
import static com.sun.jersey.api.client.ClientResponse.Status.INTERNAL_SERVER_ERROR;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;
import com.emailvision.commons.api.restful.utils.ClientHelper;
import com.leman.core.api.dictionar.client.AbstractDictionarCoreApiClient;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class AnagramCoreApiClient extends AbstractDictionarCoreApiClient {
	
	public AnagramCoreApiClient(final String dictionarCoreApiServerFormat, final Integer timeout, final Boolean debugMode) {
		super(dictionarCoreApiServerFormat, timeout, debugMode);
	}
	
	public AnagramCoreApiClient(final Integer timeout, final Boolean debugMode) {
		super(timeout, debugMode);
	}
	
	public WordEntity getRandomWord(final String hostname) throws AnagramCoreApiException, IOException {
		
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final ClientResponse clientResponse = getPartialRequestBuilder(hostname).get(ClientResponse.class);
		
		checkResponseStatus(clientResponse);
		
		return clientResponse.getEntity(WordEntity.class);
	}

	public Set<WordEntity> getWordAnagrams(final String hostname, final String sortedChars, final Boolean areDiacriticsPresent) throws AnagramCoreApiException, IOException {
		
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final ClientResponse clientResponse = getPartialRequestBuilder(hostname, sortedChars, areDiacriticsPresent).get(ClientResponse.class);
		
		checkResponseStatus(clientResponse);
		
		return clientResponse.getEntity(new GenericType<HashSet<WordEntity>>() {});
	}

	
	public WordEntity postWord(final String hostname, final String word, final String langId) throws AnagramCoreApiException, IOException {

		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		isBlankThrowIllegalArgumentException(word, format(ERROR_MISSING_PARAMETER, "word"));
		isBlankThrowIllegalArgumentException(langId, format(ERROR_MISSING_PARAMETER, "langId"));

		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("word", String.valueOf(word));
		queryParams.add("langId", String.valueOf(langId));

		final ClientResponse clientResponse = getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[] {WORD_RESOURCE_PATH}).post(ClientResponse.class);
		checkResponseStatus(clientResponse);

		return clientResponse.getEntity(WordEntity.class);
	}	
	

	public Set<WordEntity> getWordsFromDefinition(final String hostname, final String search) throws AnagramCoreApiException, IOException {
		
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final ClientResponse clientResponse = getPartialRequestBuilder(hostname, search).get(ClientResponse.class);
		
		checkResponseStatus(clientResponse);
		
		return clientResponse.getEntity(new GenericType<HashSet<WordEntity>>() {});
	}
	
	
	

//	public List<ImageEntity> getImageArchive(final String hostname, final Integer pageNumber, final Integer nbItemPerPage, final String search, final String order, final Sort sortOrder, final List<Long> managerIds) 
//			throws AnagramCoreApiException, IOException {
//		
//		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		
//		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//		for (final Long managerId : managerIds) {
//			queryParams.add(QUERY_PARAM_MANAGER_IDS, String.valueOf(managerId));
//		}
//		
//		if (order != null) {
//			queryParams.add(QUERY_PARAM_ORDER, order);
//		}
//		
//		if (sortOrder != null) {
//			queryParams.add(QUERY_PARAM_SORT, sortOrder.name());
//		}
//		
//		if (search != null) {
//			queryParams.add(QUERY_PARAM_SEARCH, search);
//		}
//		
//		final ArrayList<String> pathParams = new ArrayList<>();
//		
//		pathParams.add(IMAGE_ARCHIVE_RESOURCE_PATH);
//		
//		if (pageNumber != null && pageNumber > 0) {
//			pathParams.add(String.valueOf(pageNumber));
//			if (nbItemPerPage != null && nbItemPerPage > 0) {
//				pathParams.add(String.valueOf(nbItemPerPage));
//			}
//		}
//		
//		final ClientResponse clientResponse = getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), pathParams.toArray(new String[pathParams.size()])).get(ClientResponse.class);
//		
//		checkResponseStatus(clientResponse);
//		return clientResponse.getEntity(new GenericType<List<ImageEntity>>() {});
//	}
//
//	public List<ImageEntity> getImageArchive(final String hostname, final String search, final String order, final Sort sortOrder, final List<Long> managerIds) 
//			throws AnagramCoreApiException, IOException {
//		
//		return getImageArchive(hostname, null, null, search, order, sortOrder, managerIds);
//	}
//	
//	public List<ImageEntity> getImageArchive(final String hostname, final Integer pageNumber, final String search, final String order, final Sort sortOrder, final List<Long> managerIds) 
//			throws AnagramCoreApiException, IOException {
//		
//		return getImageArchive(hostname, pageNumber, null, search, order, sortOrder, managerIds);
//	}
//	
//	public void deleteImage(final String hostname, final List<Long> managerIds, final Long clientId, final Long imageId) throws AnagramCoreApiException, IOException {
//
//		final ClientResponse clientResponse = getPartialRequestBuilder(hostname, managerIds, clientId, imageId).delete(ClientResponse.class);
//		
//		checkResponseStatus(clientResponse);
//	}
//	
//	public void putImage(final String hostname, final List<Long> managerIds, final Long imageId, final ImageEntity requestEntity) throws AnagramCoreApiException, IOException {
//		
//		try {
//			final ClientResponse clientResponse = getPartialRequestBuilder(hostname, managerIds, imageId).put(ClientResponse.class, requestEntity);
//			checkResponseStatus(clientResponse);
//		} catch (final UniformInterfaceException | ClientHandlerException exception) {	
//			throw new AnagramCoreApiException(Status.INTERNAL_SERVER_ERROR, exception);
//		}
//	}
	
//	public ImageEntity postImage(final String hostname, final Long managerId, final Long clientId, final Long clientImageQuotaUsed, final Long clientImageMaxUsed, final File image, final String name, 
//			final String description) throws AnagramCoreApiException, IOException {
//		
//		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
//		isNegativeAndZeroThrowIllegalArgumentException(clientId, format(ERROR_MISSING_PARAMETER, "clientId"));
//		isNegativeAndZeroThrowIllegalArgumentException(managerId, format(ERROR_MISSING_PARAMETER, "managerId"));
//		isBlankThrowIllegalArgumentException(name, format(ERROR_MISSING_PARAMETER, "name"));
//		
//		if (image == null) {
//			throw new IllegalArgumentException(format(ERROR_MISSING_PARAMETER, "image"));
//		}
//
//		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//		queryParams.add(QUERY_PARAM_MANAGER_ID, String.valueOf(managerId));
//		queryParams.add(QUERY_PARAM_CLIENT_ID, String.valueOf(clientId));
//		queryParams.add(QUERY_PARAM_CLIENT_IMAGE_QUOTA_USED, String.valueOf(clientImageQuotaUsed));
//		queryParams.add(QUERY_PARAM_CLIENT_IMAGE_MAX_USED, String.valueOf(clientImageMaxUsed));
//
//		final FormDataMultiPart fdmp = new FormDataMultiPart();
//		fdmp.bodyPart(new FormDataBodyPart(FORM_PARAM_IMAGE_NAME, name));
//		
//		if (StringUtils.isNotBlank(description)) {
//			fdmp.bodyPart(new FormDataBodyPart(FORM_PARAM_IMAGE_DESC, description));
//		}
//		
//		fdmp.bodyPart(new FileDataBodyPart(FORM_PARAM_UPLOAD_FILE, image, MediaType.APPLICATION_OCTET_STREAM_TYPE));
//		
//		final ClientResponse clientResponse = getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[] {IMAGE_RESOURCE_PATH}).type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, fdmp);
//		checkResponseStatus(clientResponse);
//		
//		return clientResponse.getEntity(ImageEntity.class);
//	}
	
	private Builder getPartialRequestBuilder(final String hostname) {
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	
		return getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[] {WORD_RESOURCE_PATH, RANDOM_RESOURCE_PATH}).type(MediaType.APPLICATION_JSON);
	}
	
	private Builder getPartialRequestBuilder(final String hostname, final String sortedChars, final Boolean areDiacriticsPresent) {
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

		queryParams.add(QUERY_PARAM_SORTED_CHARS, String.valueOf(sortedChars));
		queryParams.add(QUERY_PARAM_ARE_DIACRITICS_PRESENTS, String.valueOf(areDiacriticsPresent));

		return getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[] {WORDS_RESOURCE_PATH}).type(MediaType.APPLICATION_JSON);
	}

	private Builder getPartialRequestBuilder(final String hostname, final String search) {
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

		queryParams.add(QUERY_PARAM_DEFINITION_SEARCH, String.valueOf(search));

		return getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[] {WORDS_RESOURCE_PATH, QUERY_PARAM_DEFINITION_SEARCH}).type(MediaType.APPLICATION_JSON);
	}	
	
	private Builder getPartialRequestBuilder(final MultivaluedMap<String, String> queryParams, final URI apiUrl, final String ... pathParams) {
		final Builder builder = ClientHelper.getPartialRequestBuilder(client, apiUrl, queryParams, pathParams);		
		return builder.accept(MediaType.APPLICATION_JSON);
	}
	
	private void checkResponseStatus(ClientResponse response) throws AnagramCoreApiException, IOException {
		final Status status = response.getClientResponseStatus();
		if (status.getFamily() != Family.SUCCESSFUL) {
			try {
				 throw new AnagramCoreApiException(status, response.getEntity(ExceptionEntity.class));
			} catch (final ClientHandlerException che) {
				throw new AnagramCoreApiException(INTERNAL_SERVER_ERROR, status.getStatusCode() + " " + status.getReasonPhrase());
			}
		}
	}
}
