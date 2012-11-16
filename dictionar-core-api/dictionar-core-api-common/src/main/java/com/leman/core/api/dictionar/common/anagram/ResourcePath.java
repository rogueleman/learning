package com.leman.core.api.dictionar.common.anagram;

import com.emailvision.commons.api.restful.utils.GenericResourcePath;

public final class ResourcePath extends GenericResourcePath {

	public static final String WORDS_RESOURCE_PATH = "words";
	public static final String WORD_RESOURCE_PATH = "word";
	public static final String RANDOM_RESOURCE_PATH = "random";
	
	
	public static final String QUERY_PARAM_SORTED_CHARS = "sortedChars";
	public static final String QUERY_PARAM_ARE_DIACRITICS_PRESENTS = "areDiacriticsPresent";
	
	
	public static final String QUERY_PARAM_DEFINITION_SEARCH = "definitionSearch";
	
	
	
	public static final String QUERY_PARAM_CLIENT_IMAGE_QUOTA_USED = "clientImageQuotaUsed";
	public static final String QUERY_PARAM_CLIENT_IMAGE_MAX_USED = "clientImageMaxUsed";
	
	public static final String FORM_PARAM_UPLOAD_FILE = "uploadFile";
	public static final String FORM_PARAM_IMAGE_NAME = "name";
	public static final String FORM_PARAM_IMAGE_DESC = "description";
	
	private ResourcePath() {
		
	}
}
