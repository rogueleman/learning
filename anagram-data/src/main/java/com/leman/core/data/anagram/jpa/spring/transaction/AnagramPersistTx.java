package com.leman.core.data.anagram.jpa.spring.transaction;

import static com.leman.core.data.anagram.jpa.Anagram.TRANSACTION_MANAGER_NAME;
import static com.leman.core.data.anagram.jpa.Anagram.TRANSACTION_TIMEOUT;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(propagation=REQUIRES_NEW, value=TRANSACTION_MANAGER_NAME, timeout=TRANSACTION_TIMEOUT)
public @interface AnagramPersistTx {

}
