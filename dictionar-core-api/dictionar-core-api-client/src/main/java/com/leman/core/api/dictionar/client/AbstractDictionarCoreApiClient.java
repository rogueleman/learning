package com.leman.core.api.dictionar.client;

import static org.apache.commons.lang.StringUtils.isBlank;

import javax.ws.rs.client.Client;

import static com.emailvision.commons.api.restful.support.ClientHelper.create;

public class AbstractDictionarCoreApiClient implements IAbstractDictionarCoreApiClient {

    public static final String PROPS_KEY_DICTIONAR_API_SERVER_FORMAT = "dictionar.api.url.format";
    public static final String DEFAULT_DICTIONAR_API_SERVER_FORMAT = "https://{0}/dictionar-core";
    public static final String PROPS_KEY_DICTIONAR_API_CLIENT_TIMEOUT = "core.api.client.timeout";
    public static final String PROPS_KEY_DICTIONAR_API_CLIENT_DEBUG = "core.api.client.debug";
    public static final String PROPS_KEY_DICTIONAR_API_HOST = "dictionar.api.host";

    protected final String dictionarCoreApiServerFormat;
    protected final Client client;

    public AbstractDictionarCoreApiClient(final String dictionarCoreApiServerFormat,
                                          final Integer timeout,
                                          final Boolean debugMode) {

        if (isBlank(dictionarCoreApiServerFormat)) {
            throw new NullPointerException("dictionarCoreApiServerFormat is null");
        }

        this.dictionarCoreApiServerFormat = dictionarCoreApiServerFormat;
        this.client = create(timeout, debugMode);
    }

    public AbstractDictionarCoreApiClient(final Integer timeout, final Boolean debugMode) {
        this(DEFAULT_DICTIONAR_API_SERVER_FORMAT, timeout, debugMode);
    }

    @Override
    public void close() {
        client.close();
    }
}
