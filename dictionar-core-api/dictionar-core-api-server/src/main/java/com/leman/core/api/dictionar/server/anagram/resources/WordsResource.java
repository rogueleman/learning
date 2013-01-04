package com.leman.core.api.dictionar.server.anagram.resources;

import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_ARE_DIACRITICS_PRESENTS;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_DEFINITION_SEARCH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_SORTED_CHARS;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORDS_RESOURCE_PATH;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailvision.commons.api.restful.resources.AbstractRestFulResource;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.leman.core.api.dictionar.common.anagram.entities.DefinitionEntity;
import com.leman.core.api.dictionar.server.anagram.services.IDefinitionService;
import com.leman.core.api.dictionar.server.anagram.services.IWordService;
import com.sun.jersey.spi.resource.Singleton;

@Component
@Singleton
@Path(WORDS_RESOURCE_PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
public class WordsResource extends AbstractRestFulResource {

	private final IWordService wordService;
	
	private final IDefinitionService definitionService;

//	private static final String PAGE_NUMBER_REGEX_PATH = "{pageNumber: [0-9]+}";
//
//	private static final String PAGE_NUMBER_AND_ITEM_REGEX_PATH = PAGE_NUMBER_REGEX_PATH + "/{nbItemPerPage: [0-9]+}";
//	
//	private static final String NB_ITEM_PER_PAGE = "nbItemPerPage";
//
//	private static final String PAGE_NUMBER = "pageNumber";
	
	@Autowired
	public WordsResource(final IWordService wordService, final IDefinitionService definitionService) {
		this.wordService = wordService;
		this.definitionService = definitionService;
	}

	@GET
	public Response getAllAnagramListForWord(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @QueryParam(QUERY_PARAM_SORTED_CHARS) final String sortedChars, @QueryParam(QUERY_PARAM_ARE_DIACRITICS_PRESENTS) final Boolean areDiacriticsPresent) {
		final Set<WordEntity> anagramEntities = wordService.getAllAnagramListForWord(sortedChars, areDiacriticsPresent);
		return buildGetResponse(requestHeader, new GenericEntity<Set<WordEntity>>(anagramEntities){});
	}

	
	@GET
	@Path(QUERY_PARAM_DEFINITION_SEARCH)
	public Response getWordsFromDefinition(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @QueryParam(QUERY_PARAM_DEFINITION_SEARCH) final String search) {
		final Set<DefinitionEntity> definitionEntities = definitionService.getDefinitionListWithBeginingChars(search);
		return buildGetResponse(requestHeader, new GenericEntity<Set<DefinitionEntity>>(definitionEntities){});
	}
	
	
//	@GET
//	@Path(PAGE_NUMBER_REGEX_PATH)
//	public Response getImageByPageNumber(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @PathParam(PAGE_NUMBER) final Integer pageNumber, @QueryParam(QUERY_PARAM_SEARCH) final String search, @QueryParam(QUERY_PARAM_ORDER) final String order, @QueryParam(QUERY_PARAM_SORT) final Sort sort, @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds) {
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		final List<WordEntity> imageEntities = wordService.getArchives(pageNumber, search, order, sort, managerIds);
//        return buildGetResponse(requestHeader, new GenericEntity<List<WordEntity>>(imageEntities){});
//	}
//
//	@GET
//	@Path(PAGE_NUMBER_AND_ITEM_REGEX_PATH)
//	public Response getImageByPageNumberAndNbItemPerPage(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @PathParam(PAGE_NUMBER) final Integer pageNumber, @PathParam(NB_ITEM_PER_PAGE) final Integer nbItemPerPage, @QueryParam(QUERY_PARAM_SEARCH) final String search, @QueryParam(QUERY_PARAM_ORDER) final String order, @QueryParam(QUERY_PARAM_SORT) final Sort sort, @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds) {
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		final List<WordEntity> imageEntities = wordService.getArchives(pageNumber, nbItemPerPage, search, order, sort, managerIds);
//		return buildGetResponse(requestHeader, new GenericEntity<List<WordEntity>>(imageEntities){});
//	}

}
