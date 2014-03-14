package com.leman.core.api.dictionar.server.conf;

import com.leman.core.data.dictionar.spring.config.DictionarDatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import static org.springframework.context.annotation.FilterType.CUSTOM;


@Configuration
@PropertySource(value = { "file:${env}/dictionar-core/global.properties" })
@ComponentScan(basePackages = {
		"com.leman.core.api.dictionar.server.resources",
		"com.leman.core.api.dictionar.server.services"},
		excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class),
		@Filter(type = CUSTOM, value = WebPackageFilter.class) })
@Import({ DictionarDatabaseConfiguration.class })
public class DictionarSpringConf {

	@Autowired
	private Environment env;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
