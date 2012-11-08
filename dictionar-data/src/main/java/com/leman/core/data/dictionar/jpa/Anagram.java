package com.leman.core.data.dictionar.jpa;

import org.hibernate.dialect.MySQLDialect;
import org.springframework.orm.jpa.vendor.Database;

/**
 * Constants relative to CCDB and JPA.
 */
public final class Anagram {

    /**
     * The package to scan to find all JPA entities and repositories. Used by
     * both Spring and JPA.
     */
    public static final String BASE_PACKAGE = "com.leman.core.data.dictionar.jpa";

    /**
     * The package to scan to find all JPA entities. Used by both Spring and
     * JPA.
     */
    public static final String BASE_PACKAGE_ENTITY = BASE_PACKAGE + ".domain";

    /**
     * The package to scan to find all JPA repositories. Used by both Spring and
     * JPA.
     */
    public static final String BASE_PACKAGE_REPOSITORY = BASE_PACKAGE + ".repository";

    /**
     * The name of the persistence unit.
     */
    public static final String PERSISTENCE_UNIT_NAME = "anagramPersistenceUnit";

    /**
     * The name of the transaction manager.
     */
    public static final String TRANSACTION_MANAGER_NAME = "anagramTransactionManager";

    /**
     * The timeout for each transaction
     */
    public static final int TRANSACTION_TIMEOUT = 30000;
    
    /**
     * The name of the data source.
     */
    public static final String DATASOURCE_NAME = "dictionar";

    /**
     * The SQL Dialect specifying the database platform.
     */
    public static final String DATASOURCE_PLATFORM = MySQLDialect.class.getName();

    /**
     * The database platform.
     */
    public static final Database DATABASE = Database.MYSQL;

    /**
     * 
     */
    public static final String CACHE_NAME = "repositoryCache";
    
    private Anagram() {
        // This utility class doesn't need to be instantiated.
    }
}
