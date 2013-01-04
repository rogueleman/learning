package com.leman.core.api.dictionar.server.anagram.resources;

import static com.emailvision.commons.api.restful.utils.GenericResourcePath.ERROR_MISSING_PARAMETER;
import static com.emailvision.commons.http.utils.ParamChecker.isNullThrowIllegalArgumentException;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.RANDOM_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORD_RESOURCE_PATH;
import static java.text.MessageFormat.format;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailvision.commons.api.restful.resources.AbstractRestFulResource;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import com.leman.core.api.dictionar.server.anagram.services.IWordService;
import com.sun.jersey.spi.resource.Singleton;

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
	
//	@POST
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response postImage(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @QueryParam(QUERY_PARAM_MANAGER_ID) final Long managerId, 
//			@QueryParam(QUERY_PARAM_CLIENT_ID) @DefaultValue("0") final Long clientId, @QueryParam(QUERY_PARAM_CLIENT_IMAGE_QUOTA_USED) final Long clientImageQuotaUsed,
//			@QueryParam(QUERY_PARAM_CLIENT_IMAGE_MAX_USED) @DefaultValue("0") final Long clientImageMaxUsed, @FormDataParam(FORM_PARAM_UPLOAD_FILE) final InputStream uploadedInputStream, 
//			@FormDataParam(FORM_PARAM_UPLOAD_FILE) final FormDataContentDisposition fileDetail, @FormDataParam(FORM_PARAM_IMAGE_NAME) final String name, @FormDataParam(FORM_PARAM_IMAGE_DESC) final String description) 
//					throws IOException {
//		isNegativeAndZeroThrowIllegalArgumentException(managerId, format(ERROR_MISSING_PARAMETER, "managerId"));
//		isNegativeAndZeroThrowIllegalArgumentException(clientId, format(ERROR_MISSING_PARAMETER, "clientId"));
//		isNullThrowIllegalArgumentException(name, format(ERROR_MISSING_PARAMETER, "name"));
//		
//		if (uploadedInputStream == null) {
//			throw new IllegalArgumentException(format(ERROR_MISSING_PARAMETER, "fileImage"));
//		}
//		final WordEntity entity = wordService.postImage(managerId, clientId, clientImageQuotaUsed, clientImageMaxUsed, (name != null) ? name.trim() : name, (description != null) ? description.trim() : description, uploadedInputStream,
//				fileDetail);
//		return buildPostResponse(requestHeader, entity, entity.getImageId());
//	}
//	
//	@PUT
//	@Path(WORD_ID_REGEX_PATH)
//	public Response putImage(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds, @PathParam(WORD_ID_PATH) final Long imageId, 
//			final WordEntity entity) {
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		wordService.putImage(managerIds, imageId, entity.getName(), entity.getDescription());
//		return buildPutResponse(requestHeader, entity);
//	}
//	
//	@DELETE
//	@Path(WORD_ID_REGEX_PATH)
//	public Response deleteImage(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader,  @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds, @QueryParam(QUERY_PARAM_CLIENT_ID) @DefaultValue("0") final Long clientId, @PathParam(WORD_ID_PATH) final Long imageId) {
//		isBlankListThrowIllegalArgumentException(managerIds, format(ERROR_MISSING_PARAMETER, "managerIds"));
//		isNegativeAndZeroThrowIllegalArgumentException(clientId, format(ERROR_MISSING_PARAMETER, "clientId"));
//		wordService.deleteImage(managerIds, clientId, imageId);
//		return buildDeleteResponse(requestHeader);
//	}
}
