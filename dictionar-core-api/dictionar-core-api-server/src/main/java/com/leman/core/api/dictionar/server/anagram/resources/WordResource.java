package com.leman.core.api.dictionar.server.anagram.resources;

import static com.emailvision.commons.api.restful.utils.GenericResourcePath.ERROR_MISSING_PARAMETER;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_CLIENT_ID;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_MANAGER_ID;
import static com.emailvision.commons.api.restful.utils.GenericResourcePath.QUERY_PARAM_MANAGER_IDS;
import static com.emailvision.commons.http.utils.ParamChecker.isBlankListThrowIllegalArgumentException;
import static com.emailvision.commons.http.utils.ParamChecker.isNegativeAndZeroThrowIllegalArgumentException;
import static com.emailvision.commons.http.utils.ParamChecker.isNullThrowIllegalArgumentException;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.FORM_PARAM_IMAGE_DESC;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.FORM_PARAM_IMAGE_NAME;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.FORM_PARAM_UPLOAD_FILE;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.WORD_RESOURCE_PATH;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_CLIENT_IMAGE_MAX_USED;
import static com.leman.core.api.dictionar.common.anagram.ResourcePath.QUERY_PARAM_CLIENT_IMAGE_QUOTA_USED;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailvision.commons.api.restful.resources.AbstractRestFulResource;
import com.leman.core.api.dictionar.common.anagram.entities.AnagramEntity;
import com.leman.core.api.dictionar.server.anagram.services.IWordsService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

@Path(WORD_RESOURCE_PATH)
@Component
@Singleton
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
public class WordResource extends AbstractRestFulResource {
	
	private final IWordsService wordService;

	private static final String WORD_ID_REGEX_PATH = "{wordId: [0-9]+}";
	private static final String WORD_ID_PATH = "wordId";

	@Autowired
	public WordResource(final IWordsService wordService) {
		this.wordService = wordService;
	}
	
	@GET
	public Response getWord(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader) {
		return buildGetResponse(requestHeader, wordService.getAnagramEntity());
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
//		final AnagramEntity entity = wordService.postImage(managerId, clientId, clientImageQuotaUsed, clientImageMaxUsed, (name != null) ? name.trim() : name, (description != null) ? description.trim() : description, uploadedInputStream,
//				fileDetail);
//		return buildPostResponse(requestHeader, entity, entity.getImageId());
//	}
//	
//	@PUT
//	@Path(WORD_ID_REGEX_PATH)
//	public Response putImage(@HeaderParam(HEADER_ACCESS_CONTROL_REQUEST_HEADERS) final String requestHeader, @QueryParam(QUERY_PARAM_MANAGER_IDS) final List<Long> managerIds, @PathParam(WORD_ID_PATH) final Long imageId, 
//			final AnagramEntity entity) {
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
