package com.poc.file.header;

import static com.poc.file.header.constants.AllowedMediaTypes.DOC;
import static com.poc.file.header.constants.AllowedMediaTypes.DOCX;
import static com.poc.file.header.constants.AllowedMediaTypes.GIF;
import static com.poc.file.header.constants.AllowedMediaTypes.HTML;
import static com.poc.file.header.constants.AllowedMediaTypes.JPG;
import static com.poc.file.header.constants.AllowedMediaTypes.PDF;
import static com.poc.file.header.constants.AllowedMediaTypes.PNG;
import static com.poc.file.header.constants.AllowedMediaTypes.XLS;
import static com.poc.file.header.constants.AllowedMediaTypes.XLSX;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import com.poc.file.header.constants.AllowedMediaTypes;
import com.poc.file.header.entity.FileInfo;
import org.junit.Test;

public class TestFileHeaderTest {

    TestFileHeader testFileHeader = new TestFileHeader("attachment");

    @Test
    public void when_AllIsOk_when_testing_with_pdf_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.pdf").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(PDF.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_png_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.png").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(PNG.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_xpng_file_mediatype_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.png").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(PNG.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_gif_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.gif").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(GIF.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_spaces_in_file_name_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(URLDecoder.decode(classLoader.getResource("attachment/test jira.gif").getFile(), "UTF-8"));

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(GIF.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_animated_gif_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/bad.gif").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(GIF.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_jpeg_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.jpeg").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(JPG.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_jpg_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.jpg").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(JPG.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_doc_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.doc").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(DOC.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_xls_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.xls").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(XLS.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_docx_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.docx").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(DOCX.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_xlsx_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.xlsx").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(XLSX.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_html_file_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.html").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        //tike library returns: "text/html; charset=UTF-8"
        assertThat(fileInfo.getFileMetadata()).contains(HTML.getContentType());
        //assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_directory_then_ok() throws IOException {

        final Map<String, String> fileHeaderWithTika = testFileHeader.getFileHeaderWithTika();

        assertThat(fileHeaderWithTika).isNotNull();
        assertThat(fileHeaderWithTika).isNotEmpty();
    }

    @Test
    public void when_AllIsOk_when_testing_with_file_renamed_from_pdf_to_doc_then_ko() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/testbmp.doc").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase("has no acceptable media type");
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isFalse();
    }

    @Test
    public void when_AllIsOk_when_testing_with_file_renamed_from_gif_to_pdf_then_ko() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/testgif.pdf").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isNotEqualTo(PDF.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_xyz_file_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.xyz").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase("chemical/x-xyz");
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isFalse();
    }

    @Test
    public void when_AllIsOk_when_testing_with_jpg_5Mb_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(URLDecoder.decode(classLoader.getResource("attachment/test 5Mb.jpg").getFile(), "UTF-8"));

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(JPG.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_jpg_19Mb_file_ok_then_ok() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(URLDecoder.decode(classLoader.getResource("attachment/test 19Mb.jpg").getFile(), "UTF-8"));

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).isEqualToIgnoringCase(JPG.getContentType());
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isTrue();
    }

    @Test
    public void when_AllIsOk_when_testing_with_txt_file_ok_then_ko() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.txt").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).contains("text/plain");
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isFalse();
    }

    @Test
    public void when_AllIsOk_when_testing_with_bmp_file_ok_then_ko() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("attachment/test.bmp").getFile());

        final FileInfo fileInfo = testFileHeader.withTika(file);

        assertThat(fileInfo.getFileName()).isEqualToIgnoringCase(file.getName());
        assertThat(fileInfo.getFileMetadata()).contains("image/x-ms-bmp");
        assertThat(AllowedMediaTypes.isContentTypeAllowed(fileInfo.getFileMetadata())).isFalse();
    }

}