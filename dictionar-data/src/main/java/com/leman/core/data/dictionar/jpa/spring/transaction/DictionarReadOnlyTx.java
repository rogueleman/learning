package com.leman.core.data.dictionar.jpa.spring.transaction;

import static com.leman.core.data.dictionar.jpa.Dictionar.TRANSACTION_MANAGER_NAME;
import static com.leman.core.data.dictionar.jpa.Dictionar.TRANSACTION_TIMEOUT;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = TRANSACTION_MANAGER_NAME, readOnly = true, timeout = TRANSACTION_TIMEOUT)
public @interface DictionarReadOnlyTx {

}
