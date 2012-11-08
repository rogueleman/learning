package com.leman.core.api.dictionar.common.anagram;


public enum MediaErrorType {

    ERROR_CLIENT_QUOTA_MAX(1, "The image can not be uploaded. Quota max will be exceeded."),
    ERROR_IMG_CONTENT_TYPE(2, "The image has an invalid content-type."),
    ERROR_IMG_LENGHT(3, "The image is too big. It must be less than 2 Mb."),
    ERROR_IMG_INVALID(4, "The image has an invalid extension."),
    ERROR_IMG_GENERATE_ID(5, "Cannot generate random id"),
    ERROR_FTP_ERROR(6, "Please retry later.");
    
    private final int errorCode;
    private final String errorMessage;
    
    private MediaErrorType(final int errorCode, final String errorMessage) {
    	 this.errorCode = errorCode;
    	 this.errorMessage = errorMessage;
	}
    
    public int getErrorCode() {
		return errorCode;
	}
    
    public String getErrorMessage() {
		return errorMessage;
	}
}
