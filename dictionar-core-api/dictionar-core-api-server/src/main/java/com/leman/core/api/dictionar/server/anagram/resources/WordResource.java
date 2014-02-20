package com.leman.core.api.dictionar.server.anagram.resources;

import com.emailvision.commons.api.restful.resources.AbstractRestFulResource;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.leman.core.api.dictionar.server.anagram.services.IWordService;
import com.sun.jersey.spi.resource.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.emailvision.commons.http.utils.ParamChecker.isNullThrowIllegalArgumentException;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.*;
import static java.text.MessageFormat.format;

@Path(WORD_RESOURCE_PATH)
@Component
@Singleton
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
public class WordResource extends AbstractRestFulResource {
	
	private final IWordService wordService;

	private static final String WORD_ID_REGEX_PATH = "{wordId: [0-9]+}";
	private static final String WORD_ID_PATH = "wordId";

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
	public Response postWord(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, final String word, final Integer langId) throws IOException{
		isNullThrowIllegalArgumentException(word, format(ERROR_MISSING_PARAMETER, "word"));
		isNullThrowIllegalArgumentException(langId, format(ERROR_MISSING_PARAMETER, "lang"));
		
		WordEntity wordEntity = wordService.postWord(word, langId);
		return buildPutResponse(requestHeader, wordEntity);
	}
}
