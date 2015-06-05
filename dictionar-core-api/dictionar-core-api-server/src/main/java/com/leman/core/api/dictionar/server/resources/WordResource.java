package com.leman.core.api.dictionar.server.resources;

import static com.leman.anagram.Language.getByLang;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.ERROR_MISSING_PARAMETER;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.RANDOM_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORD_RESOURCE_PATH;
import static java.text.MessageFormat.format;

import java.io.IOException;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.leman.core.api.dictionar.server.services.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.emailvision.commons.http.utils.ParamChecker.isNullThrowIllegalArgumentException;

import com.emailvision.commons.api.restful.resources.AbstractRestFulResource;

@Path(WORD_RESOURCE_PATH)
@Component
@Singleton
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
public class WordResource extends AbstractRestFulResource {

    private final IWordService wordService;

    @Autowired
    public WordResource(final IWordService wordService) {
        this.wordService = wordService;
    }

    @GET
    @Path(RANDOM_RESOURCE_PATH)
    public Response getRandomWord(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader) {
        return buildGetResponse(requestHeader, wordService.getWordEntityForRandomWord());
    }

    @POST
    public Response postWord(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader,
                             final WordEntity wordEntity) throws IOException {
        isNullThrowIllegalArgumentException(wordEntity.getWord(), format(ERROR_MISSING_PARAMETER, "word"));
        isNullThrowIllegalArgumentException(wordEntity.getLang(), format(ERROR_MISSING_PARAMETER, "lang"));

        return buildPutResponse(requestHeader, wordService.postWord(wordEntity.getWord(),
                                                                    getByLang(wordEntity.getLang()).getValue()));
    }
}
