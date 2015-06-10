package com.leman.core.api.dictionar.client.anagram;

import static com.leman.core.api.dictionar.common.anagram.ResourcePath.ERROR_MISSING_PARAMETER;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_ARE_DIACRITICS_PRESENTS;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_CHARS;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_DEFINITION_SEARCH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.RANDOM_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORDS_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORD_RESOURCE_PATH;
import static java.text.MessageFormat.format;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.leman.core.api.dictionar.client.AbstractDictionarCoreApiClient;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.emailvision.commons.http.utils.ParamChecker.isBlankThrowIllegalArgumentException;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;
import com.emailvision.commons.api.restful.support.ClientHelper;

public final class AnagramCoreApiClient extends AbstractDictionarCoreApiClient {

    public static final Logger LOGGER = LoggerFactory.getLogger(AnagramCoreApiClient.class);

    public AnagramCoreApiClient(final String dictionarCoreApiServerFormat,
                                final Integer timeout,
                                final Boolean debugMode) {
        super(dictionarCoreApiServerFormat, timeout, debugMode);
    }

    public AnagramCoreApiClient(final Integer timeout, final Boolean debugMode) {
        super(timeout, debugMode);
    }

    public WordEntity getRandomWord(final String hostname) throws AnagramCoreApiException, IOException {

        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));

        final Response clientResponse = getPartialRequestBuilder(hostname).get(Response.class);

        return checkResponseStatus(clientResponse, WordEntity.class);
    }

    public Set<WordEntity> getWordAnagrams(final String hostname,
                                           final String sortedChars,
                                           final Boolean areDiacriticsPresent)
            throws AnagramCoreApiException, IOException {

        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));

        final Response clientResponse = getPartialRequestBuilder(hostname, sortedChars, areDiacriticsPresent).get(
                Response.class);

        return checkResponseStatus(clientResponse, new GenericType<HashSet<WordEntity>>() {
        });
    }

    public Future<WordEntity> postWord(final String hostname, final WordEntity wordEntity)
            throws AnagramCoreApiException, IOException {

        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));
        isBlankThrowIllegalArgumentException(wordEntity.getWord(), format(ERROR_MISSING_PARAMETER, "word"));
        isBlankThrowIllegalArgumentException(wordEntity.getLang(), format(ERROR_MISSING_PARAMETER, "langId"));

        final MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("word", String.valueOf(wordEntity.getWord()));
        queryParams.add("langId", String.valueOf(wordEntity.getLang()));

        final Future<WordEntity> post = getPartialRequestBuilder(queryParams,
                                                                 UriBuilder.fromUri(format(dictionarCoreApiServerFormat,
                                                                                           hostname)).build(),
                                                                 new String[] { WORD_RESOURCE_PATH })
                .async()
                .post(Entity.entity(wordEntity, MediaType.APPLICATION_JSON_TYPE), new InvocationCallback<WordEntity>() {
                    @Override
                    public void completed(WordEntity response) {
                        LOGGER.info(format("ANAGRAM CORE # POST WORD SUCCESFUL # WORD ID {0} # WORD {1}"),
                                    response.getId(),
                                    response.getWord());
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        LOGGER.info(format("ANAGRAM CORE # POST WORD UNSUCCESFUL # WORD ID {0} # WORD {1}"),
                                    wordEntity.getId(),
                                    wordEntity.getWord());
                    }
                });

        return post;
    }

    public Set<WordEntity> getWordsFromDefinition(final String hostname, final String search)
            throws AnagramCoreApiException, IOException {

        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));

        final Response clientResponse = getPartialRequestBuilder(hostname, search).get(Response.class);

        return checkResponseStatus(clientResponse, new GenericType<HashSet<WordEntity>>() {
        });
    }

    private Builder getPartialRequestBuilder(final String hostname) {
        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));

        final MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();

        return getPartialRequestBuilder(queryParams,
                                        UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(),
                                        new String[] { WORD_RESOURCE_PATH, RANDOM_RESOURCE_PATH });
    }

    private Builder getPartialRequestBuilder(final String hostname,
                                             final String sortedChars,
                                             final Boolean areDiacriticsPresent) {
        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));

        final MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();

        queryParams.add(QUERY_PARAM_CHARS, String.valueOf(sortedChars));
        queryParams.add(QUERY_PARAM_ARE_DIACRITICS_PRESENTS, String.valueOf(areDiacriticsPresent));

        return getPartialRequestBuilder(queryParams,
                                        UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(),
                                        new String[] { WORDS_RESOURCE_PATH });
    }

    private Builder getPartialRequestBuilder(final String hostname, final String search) {
        isBlankThrowIllegalArgumentException(hostname, format(ERROR_MISSING_PARAMETER, "hostname"));

        final MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();

        queryParams.add(QUERY_PARAM_DEFINITION_SEARCH, String.valueOf(search));

        return getPartialRequestBuilder(queryParams,
                                        UriBuilder.fromUri(format(dictionarCoreApiServerFormat, hostname)).build(),
                                        new String[] { WORDS_RESOURCE_PATH, QUERY_PARAM_DEFINITION_SEARCH });
    }

    private Builder getPartialRequestBuilder(final MultivaluedMap<String, String> queryParams,
                                             final URI apiUrl,
                                             final String... pathParams) {
        return ClientHelper.getPartialRequestBuilder(client, apiUrl, queryParams, pathParams);
    }

    private <T> T checkResponseStatus(final Response response, final GenericType<T> gt) throws AnagramCoreApiException {
        try {
            final Status status = Status.fromStatusCode(response.getStatus());
            if (status.getFamily() != SUCCESSFUL) {
                throw new AnagramCoreApiException(status, response.readEntity(ExceptionEntity.class));
            }
            return response.readEntity(gt);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private <T> T checkResponseStatus(final Response response, final Class<T> type) throws AnagramCoreApiException {
        try {
            final Status status = Status.fromStatusCode(response.getStatus());
            if (status.getFamily() != SUCCESSFUL) {
                if (status == NOT_FOUND) {
                    return null;
                }
                throw new AnagramCoreApiException(status, response.readEntity(ExceptionEntity.class));
            }
            if (status == NOT_FOUND || Void.TYPE == type) {
                return null;
            }
            return response.readEntity(type);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
