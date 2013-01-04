package com.leman.core.data.dictionar.spring.config;

import static com.leman.core.data.dictionar.jpa.Dictionar.BASE_PACKAGE_ENTITY;
import static com.leman.core.data.dictionar.jpa.Dictionar.BASE_PACKAGE_REPOSITORY;
import static com.leman.core.data.dictionar.jpa.Dictionar.DATABASE;
import static com.leman.core.data.dictionar.jpa.Dictionar.DATASOURCE_NAME;
import static com.leman.core.data.dictionar.jpa.Dictionar.DATASOURCE_PLATFORM;
import static com.leman.core.data.dictionar.jpa.Dictionar.PERSISTENCE_UNIT_NAME;
import static com.leman.core.data.dictionar.jpa.Dictionar.TRANSACTION_MANAGER_NAME;
import static com.emailvision.data.jpa.JPA.HIBERNATE_PROPERTIES;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.emailvision.commons.pool.InjectableDataSource;


@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@ComponentScan(basePackages = { BASE_PACKAGE_REPOSITORY }, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
public class DictionarDatabaseConfiguration {

    @Bean
    public DataSource dictionarDataSource() {
        return new InjectableDataSource(DATASOURCE_NAME);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean wordEntityManagerFactory() {

        final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dictionarDataSource());
        entityManagerFactory.setPackagesToScan(BASE_PACKAGE_ENTITY);
        entityManagerFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setDatabasePlatform(DATASOURCE_PLATFORM);
        vendorAdapter.setDatabase(DATABASE);
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        entityManagerFactory.setJpaPropertyMap(HIBERNATE_PROPERTIES);

        return entityManagerFactory;
    }
    
    @Bean(name=TRANSACTION_MANAGER_NAME)
    public PlatformTransactionManager ccdbTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(wordEntityManagerFactory().getObject());
        transactionManager.setDataSource(dictionarDataSource());
        return transactionManager;
    }
    

}
