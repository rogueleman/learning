package com.poc.file.header;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poc.file.header.entity.FileInfo;
import org.apache.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

/**
 * Created by ggutau
 */
public class TestFileHeader {

    private static final Logger LOGGER = Logger.getLogger(TestFileHeader.class);

    String resourcesFolder;

    public TestFileHeader(String resourcesFolder) {
        this.resourcesFolder = resourcesFolder;
    }

    public Map<String, String> getFileHeaderWithTika() throws IOException {
        final List<File> files = getFiles();
        Map<String, String> fileNameMetadata = new HashMap<>();
        for (File file : files) {
            FileInfo fileInfo = withTika(file);
            fileNameMetadata.put(fileInfo.getFileName(), fileInfo.getFileMetadata());
        }
        return fileNameMetadata;
    }

    public FileInfo withTika(File file) throws IOException {
        FileInputStream is = null;

        FileInfo fileInfo = null;
        try {
            is = new FileInputStream(file);

            ContentHandler contenthandler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            Parser parser = new AutoDetectParser();
/*            String fileExtension = getFileExtension(file);
            if (fileExtension.equals(DOCX.getExtensions().get(0))
                || fileExtension.equals(XLSX.getExtensions().get(0))) {
                parser = new OOXMLParser();
            }
            if (fileExtension.equals(DOC.getExtensions().get(0))
                || fileExtension.equals(XLS.getExtensions().get(0))) {
                parser = new OfficeParser();
            }*/

            parser.parse(is, contenthandler, metadata, new ParseContext());
            fileInfo = new FileInfo(file.getName(), metadata.get(Metadata.CONTENT_TYPE));
        }
        catch (Exception e) {
            fileInfo = new FileInfo(file.getName(), "has no acceptable media type");
        }
        finally {
            if (is != null) is.close();
        }

        return fileInfo;
    }

/*    private String getFileExtension(File file) {
        final String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }*/

    private List<File> getFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file1 = new File(classLoader.getResource(resourcesFolder).getFile());
        final Path directory = Paths.get(file1.toURI());
        final List<File> files = new ArrayList<>();
        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (final Path path : directoryStream) {
                if (Files.isDirectory(directory)) {
                    files.add(path.toFile());
                }
            }
        } catch (IOException ex) {
        }
        return files;
    }
}
