package com.leman.core.api.dictionar.server.anagram.resources;

import static com.emailvision.commons.api.restful.utils.GenericResourcePath.ERROR_MISSING_PARAMETER;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_MANAGER_IDS;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_ORDER;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_SEARCH;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_SORT;
import static com.emailvision.commons.http.utils.ParamChecker.isBlankListThrowIllegalArgumentException;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORD_ARCHIVE_RESOURCE_PATH;
import static java.text.MessageFormat.format;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailvision.commons.api.restful.resources.AbstractRestFulResource;
import com.emailvision.core.constants.jpa.Sort;
import com.leman.core.api.dictionar.common.anagram.entities.AnagramEntity;
import com.leman.core.api.dictionar.server.anagram.services.IWordsService;
import com.sun.jersey.spi.resource.Singleton;

@Component
@Singleton
@Path(WORD_ARCHIVE_RESOURCE_PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
public class WordsResource extends AbstractRestFulResource {

	private final IWordsService wordsService;

	private static final String PAGE_NUMBER_REGEX_PATH = "{pageNumber: [0-9]+}";

	private static final String PAGE_NUMBER_AND_ITEM_REGEX_PATH = PAGE_NUMBER_REGEX_PATH + "/{nbItemPerPage: [0-9]+}";
	
	private static final String NB_ITEM_PER_PAGE = "nbItemPerPage";

	private static final String PAGE_NUMBER = "pageNumber";
	
	
	@Autowired
	public WordsResource(final IWordsService wordsService) {
		this.wordsService = wordsService;
	}

//	@GET
//	public Response getImages(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @QueryParam(QUERY_PARAM_SEARCH) final String search, @QueryParam(QUERY_PARAM_ORDER) final String order, @QueryParam(QUERY_PARAM_SORT) final Sort sort) {
//		final List<AnagramEntity> anagramEntities = wordsService.getArchives(search, order, sort);
//		return buildGetResponse(requestHeader, new GenericEntity<List<AnagramEntity>>(anagramEntities){});
//	}

//	@GET
//	@Path(PAGE_NUMBER_REGEX_PATH)
//	public Response getImageByPageNumber(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @PathParam(PAGE_NUMBER) final Integer pageNumber, @QueryParam(QUERY_PARAM_SEARCH) final String search, @QueryParam(QUERY_PARAM_ORDER) final String order, @QueryParam(QUERY_PARAM_SORT) final Sort sort, @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds) {
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		final List<AnagramEntity> imageEntities = wordsService.getArchives(pageNumber, search, order, sort, managerIds);
//        return buildGetResponse(requestHeader, new GenericEntity<List<AnagramEntity>>(imageEntities){});
//	}
//
//	@GET
//	@Path(PAGE_NUMBER_AND_ITEM_REGEX_PATH)
//	public Response getImageByPageNumberAndNbItemPerPage(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @PathParam(PAGE_NUMBER) final Integer pageNumber, @PathParam(NB_ITEM_PER_PAGE) final Integer nbItemPerPage, @QueryParam(QUERY_PARAM_SEARCH) final String search, @QueryParam(QUERY_PARAM_ORDER) final String order, @QueryParam(QUERY_PARAM_SORT) final Sort sort, @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds) {
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		final List<AnagramEntity> imageEntities = wordsService.getArchives(pageNumber, nbItemPerPage, search, order, sort, managerIds);
//		return buildGetResponse(requestHeader, new GenericEntity<List<AnagramEntity>>(imageEntities){});
//	}

}
