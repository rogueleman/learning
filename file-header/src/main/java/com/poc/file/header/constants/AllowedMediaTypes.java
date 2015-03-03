package com.poc.file.header.constants;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;

public enum AllowedMediaTypes {
    PDF("application/pdf", new String[] { "pdf" }),
    PNG("image/png", new String[] { "png" }),
    XPNG("image/x-png", new String[] { "png" }),
    GIF("image/gif", new String[] { "gif" }),
    JPG("image/jpeg", new String[] { "jpg", "jpeg" }),
    DOC("application/msword", new String[] { "doc" }),
    XLS("application/vnd.ms-excel", new String[] { "xls" }),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", new String[] { "docx" }),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new String[] { "xlsx" }),
    HTML("text/html", new String[] { "html" }),
    DEFAULT("application/octet-stream", new String[] { "docx", "xlsx", "pdf", "png", "jpeg", "jpg",
                                                                     "gif", "doc", "xls", "html" });

    private final String contentType;

    private final String[] extensions;

    private AllowedMediaTypes(String contentType, String[] extensions) {
        this.contentType = contentType;
        this.extensions = extensions;
    }

    public String getContentType() {
        return contentType;
    }

    public List<String> getExtensions() {
        return ImmutableList.copyOf(extensions);
    }

    public static AllowedMediaTypes getByContentType(final String contentType) {
        if (isNotBlank(contentType)) {
            for (AllowedMediaTypes t : AllowedMediaTypes.values()) {
                if (t.getContentType().equalsIgnoreCase(contentType)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static boolean isContentTypeAllowed(final String contentType) {
        if (isNotBlank(contentType)) {
            for (AllowedMediaTypes t : AllowedMediaTypes.values()) {
                if (t.getContentType().equalsIgnoreCase(contentType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFileAllowed(final String fileName) {
        if (isNotBlank(fileName)) {
            final String fileExtension = StringUtils.substringAfterLast(fileName, ".");
            for (AllowedMediaTypes t : AllowedMediaTypes.values()) {
                if (t.getExtensions().contains(fileExtension)) {
                    return true;
                }
            }
        }
        return false;
    }

}
