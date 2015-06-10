package com.leman.core.api.dictionar.client.anagram;

import javax.ws.rs.core.Response.Status;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;

public class AnagramCoreApiException extends Exception {

    private static final long serialVersionUID = -3244026875761944033L;

    private final ExceptionEntity entity;
    private final Status status;

    public AnagramCoreApiException(final Status status, final ExceptionEntity entity) {
        this.entity = entity;
        this.status = status;
    }

    public AnagramCoreApiException(final Status status, final Throwable exception) {
        super(exception);
        this.entity = new ExceptionEntity(exception.getMessage());
        this.status = status;
    }

    public AnagramCoreApiException(final Status status, final String errorMessage) {
        this.entity = new ExceptionEntity(errorMessage);
        this.status = status;
    }

    public ExceptionEntity getEntity() {
        return entity;
    }

    public Status getStatus() {
        return status;
    }

}
