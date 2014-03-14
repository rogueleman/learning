package com.leman.core.api.dictionar.server.conf;

import com.emailvision.commons.api.restful.filters.LoggingFilter;
import com.emailvision.commons.api.restful.json.providers.JacksonConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.LoggerFactory;

import static org.glassfish.jersey.server.ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED;

public class DictionarCoreWsApplication extends ResourceConfig {

    private static final String APPLICATION_NAME = "dictionar-core";

    /**
     * Register JAX-RS application components.
     */
    public DictionarCoreWsApplication() {
        setApplicationName(APPLICATION_NAME);
        register(RequestContextFilter.class);
        register(new JacksonFeature());
        register(new LoggingFilter(LoggerFactory.getLogger(APPLICATION_NAME), false));
        packages("com.leman.core.api.dictionar.server.resources",
                "com.emailvision.commons.api.restful.exceptions.providers.standard",
                "com.emailvision.commons.api.restful.exceptions.providers.java",
                "com.emailvision.commons.api.restful.exceptions.providers.jpa",
                "com.emailvision.commons.api.restful.exceptions.providers.spring");
        property(MONITORING_STATISTICS_MBEANS_ENABLED, true);
        register(JacksonConfig.class);
    }
}