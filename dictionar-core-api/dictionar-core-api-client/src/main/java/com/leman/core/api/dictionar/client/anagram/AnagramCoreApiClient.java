package com.leman.core.api.dictionar.client.anagram;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;
import com.emailvision.commons.api.restful.support.ClientHelper;
import com.leman.core.api.dictionar.client.AbstractDictionarCoreApiClient;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import static com.emailvision.commons.http.utils.ParamChecker.isBlankThrowIllegalArgumentException;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.*;
import static com.sun.jersey.api.client.ClientResponse.Status.INTERNAL_SERVER_ERROR;
import static java.text.MessageFormat.format;

public final class AnagramCoreApiClient extends AbstractDictionarCoreApiClient {

    public static final Logger LOGGER = LoggerFactory.getLogger(AnagramCoreApiClient.class);
	
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

	
	public Future<WordEntity> postWord(final String hostname, final WordEntity wordEntity) throws AnagramCoreApiException, IOException {

		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		isBlankThrowIllegalArgumentException(wordEntity.getWord(), format(ERROR_MISSING_PARAMETER, "word"));
		isBlankThrowIllegalArgumentException(wordEntity.getLang(), format(ERROR_MISSING_PARAMETER, "langId"));

		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("word", String.valueOf(wordEntity.getWord()));
		queryParams.add("langId", String.valueOf(wordEntity.getLang()));

		final Future<WordEntity> post = getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[]{WORD_RESOURCE_PATH})
                .async()
                .post(Entity.entity(wordEntity, MediaType.APPLICATION_JSON_TYPE), new InvocationCallback<WordEntity>() {
                    @Override
                    public void completed(WordEntity response) {
                        LOGGER.info(format ("ANAGRAM CORE # POST WORD SUCCESFUL # WORD ID {0} # WORD {1}"),
                                response.getId(),
                                response.getWord());
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        LOGGER.info(format ("ANAGRAM CORE # POST WORD UNSUCCESFUL # WORD ID {0} # WORD {1}"),
                                wordEntity.getId(),
                                wordEntity.getWord());
                    }
                });

		return post;
	}	
	
	public Set<WordEntity> getWordsFromDefinition(final String hostname, final String search) throws AnagramCoreApiException, IOException {
		
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final ClientResponse clientResponse = getPartialRequestBuilder(hostname, search).get(ClientResponse.class);
		
		checkResponseStatus(clientResponse);
		
		return clientResponse.getEntity(new GenericType<HashSet<WordEntity>>() {});
	}
	
	private Builder getPartialRequestBuilder(final String hostname) {
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
	
		return getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[]{WORD_RESOURCE_PATH, RANDOM_RESOURCE_PATH});
	}
	
	private Builder getPartialRequestBuilder(final String hostname, final String sortedChars, final Boolean areDiacriticsPresent) {
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

		queryParams.add(QUERY_PARAM_SORTED_CHARS, String.valueOf(sortedChars));
		queryParams.add(QUERY_PARAM_ARE_DIACRITICS_PRESENTS, String.valueOf(areDiacriticsPresent));

		return getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[]{WORDS_RESOURCE_PATH});
	}

	private Builder getPartialRequestBuilder(final String hostname, final String search) {
		isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
		
		final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

		queryParams.add(QUERY_PARAM_DEFINITION_SEARCH, String.valueOf(search));

		return getPartialRequestBuilder(queryParams, UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(), new String[]{WORDS_RESOURCE_PATH, QUERY_PARAM_DEFINITION_SEARCH});
	}	
	
	private Builder getPartialRequestBuilder(final MultivaluedMap<String, String> queryParams, final URI apiUrl, final String ... pathParams) {
		return ClientHelper.getPartialRequestBuilder(client, apiUrl, queryParams, pathParams);
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
