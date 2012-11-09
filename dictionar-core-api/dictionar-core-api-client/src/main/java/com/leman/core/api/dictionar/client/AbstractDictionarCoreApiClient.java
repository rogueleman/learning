package com.leman.core.api.dictionar.client;

import static com.emailvision.commons.api.restful.utils.ClientHelper.createClient;

import org.apache.commons.lang.StringUtils;

import com.sun.jersey.api.client.Client;

public abstract class AbstractDictionarCoreApiClient {

	public static final String PROPS_KEY_DICTIONAR_API_SERVER_FORMAT = "dictionar.api.url.format";
	public static final String DEFAULT_DICTIONAR_API_SERVER_FORMAT = "https://{0}/dictionar-core"; 
	public static final String PROPS_KEY_DICTIONAR_API_CLIENT_TIMEOUT = "core.api.client.timeout";
	public static final String PROPS_KEY_DICTIONAR_API_CLIENT_DEBUG = "core.api.client.debug";
	public static final String PROPS_KEY_DICTIONAR_API_HOST = "dictionar.api.host";
	
	protected final String dictionarCoreApiServerFormat;
	protected final Client client;
	
	public AbstractDictionarCoreApiClient(final String dictionarCoreApiServerFormat, final Integer timeout, final Boolean debugMode) {

		if (StringUtils.isBlank(dictionarCoreApiServerFormat)) {
			throw new NullPointerException("dictionarCoreApiServerFormat is null");
		}

		this.dictionarCoreApiServerFormat = dictionarCoreApiServerFormat;
		this.client = createClient(timeout, debugMode);
		this.client.setFollowRedirects(true);
	}
	
	public AbstractDictionarCoreApiClient(final Integer timeout, final Boolean debugMode) {
		this(DEFAULT_DICTIONAR_API_SERVER_FORMAT, timeout, debugMode);
	}
	
	public void destroy() {
		client.destroy();
	}
}
