package com.leman.core.data.dictionar.spring.config;

import static com.leman.core.data.dictionar.jpa.Dictionar.TRANSACTION_MANAGER_NAME;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.emailvision.commons.pool.init.ConnectionPoolInitializer;
import com.emailvision.data.jpa.JPA;

/**
 * 
 * WARNING: This Test use file pool.properties
 *
 */
public class DictionarDatabaseConfigurationTestITG {
	
	@Before
	public void before() throws Exception {
		//Hibernate validate existing tables 
		JPA.HIBERNATE_PROPERTIES.put("hibernate.hbm2ddl.auto", "validate");
		//Hibernate create tables (does not delete/update the existing tables)
//		JPA.HIBERNATE_PROPERTIES.put("hibernate.hbm2ddl.auto", "create");
		ConnectionPoolInitializer.init(getClass().getClassLoader().getResource("pool.properties").getFile().toString());
	}
	
	@Test
	public void should_test_init_spring_conf() {
		final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DictionarDatabaseConfiguration.class);
		assertTrue(applicationContext.containsBean("wordEntityManagerFactory"));
		assertTrue(applicationContext.containsBean(TRANSACTION_MANAGER_NAME));
		assertTrue(applicationContext.containsBean("dictionarDataSource"));
	}

}
