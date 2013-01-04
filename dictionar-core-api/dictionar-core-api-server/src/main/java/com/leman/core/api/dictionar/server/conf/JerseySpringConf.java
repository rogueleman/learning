package com.leman.core.api.dictionar.server.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.leman.core.data.dictionar.spring.config.DictionarDatabaseConfiguration;

@Configuration
@ComponentScan(basePackages = { "com.leman.core.api.dictionar.server.anagram.services", "com.leman.core.api.dictionar.server.anagram.resources"}, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
@PropertySource({"file:/etc/emv/dictionar-core/global.properties"})
@Import({DictionarDatabaseConfiguration.class})
public class JerseySpringConf {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
