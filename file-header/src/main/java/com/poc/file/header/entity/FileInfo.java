package com.poc.file.header.entity;

/**
 * Created by ggutau.
 */
public class FileInfo {
    String fileName;
    String fileMetadata;

    public FileInfo(String fileName, String fileMetadata) {
        this.fileName = fileName;
        this.fileMetadata = fileMetadata;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(String fileMetadata) {
        this.fileMetadata = fileMetadata;
    }
}
