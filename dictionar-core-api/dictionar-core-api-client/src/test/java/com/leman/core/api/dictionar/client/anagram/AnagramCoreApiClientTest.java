package com.leman.core.api.dictionar.client.anagram;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.leman.core.api.dictionar.client.AbstractDictionarCoreApiClient;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;


public class AnagramCoreApiClientTest {

	private AnagramCoreApiClient apiClient;

	
	public static WordEntity anagram1 = new WordEntity(0l, "1", "leu", "leu", "elu", "elu", 3);
	public static WordEntity anagram2 = new WordEntity(1l, "1", "meu", "meu", "emu", "emu", 3);
	public static WordEntity anagram3 = new WordEntity(1l, "1", "manuel", "manuel", "aelmnu", "aelmnu", 6);
	public static WordEntity anagram4 = new WordEntity(1l, "1", "lemn", "lemn", "elmn", "elmn", 4);
	
	private static List<WordEntity> listOfWords;

	private static void setImages(final WordEntity... anagrams) {
		listOfWords = new ArrayList<WordEntity>();
		for (final WordEntity anagramMetaEntity : anagrams) {
			listOfWords.add(anagramMetaEntity);
		}
	}
	
	@Before
	public void setUp() {
		apiClient = new AnagramCoreApiClient(AbstractDictionarCoreApiClient.DEFAULT_DICTIONAR_API_SERVER_FORMAT, 1000, true);
		AnagramCoreApiClientTest.setImages(AnagramCoreApiClientTest.anagram1, AnagramCoreApiClientTest.anagram2, AnagramCoreApiClientTest.anagram3, AnagramCoreApiClientTest.anagram4);
	}
	
	@Test
	public void should_test_second_constructor() {
		apiClient = new AnagramCoreApiClient(1000, true);
		
		assertThat(apiClient.getClass()).isEqualTo(AnagramCoreApiClient.class);
	}
	
	@Test(expected=NullPointerException.class)
	public void should_test_null_api_server_format() {
		apiClient = new AnagramCoreApiClient(null, 1000, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void should_test_getWord_when_hostname_is_null() throws Exception {
		//when
		apiClient.getRandomWord(null);
	}
	
	public void should_test_getWord_when_imageId_is_null() throws Exception {
		//when
		apiClient.getRandomWord("apiHost");
	}

//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_getImages_with_all_argument_when_hostname_is_null() throws Exception {
//		//Given
//		final ArrayList<Long> managerIds = new ArrayList<Long>();
//		managerIds.add(1L);
//		
//		//when
//		apiClient.getImageArchive(null, 1, 2, "search", "order", DESC, managerIds);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_getImages_with_all_argument_when_managerList_is_null() throws Exception {
//		//when
//		apiClient.getImageArchive("apiHost", 1, 2, "search", "order", DESC, null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_getImages_with_no_pageNumber_and_nbItemPerPage_when_managerList_is_null() throws Exception {
//		//when
//		apiClient.getImageArchive("apiHost", "search", "order", DESC, null);
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_getImages_with_no_pageNumber_and_nbItemPerPage_when_hostname_is_null() throws Exception {
//		//Given
//		final ArrayList<Long> managerIds = new ArrayList<Long>();
//		managerIds.add(1L);
//		
//		//when
//		apiClient.getImageArchive(null, "search", "order", DESC, managerIds);
//	}
//
//	public void should_test_getImages_with_no_pageNumber_and_nbItemPerPage() throws Exception {		
//		//Given
//		final ArrayList<Long> managerIds = new ArrayList<Long>();
//		managerIds.add(1L);
//		
//		//when
//		when(apiClient.getImageArchive("apiHost", null, null, "search", "order", DESC, managerIds)).thenReturn(listOfImage);
//		final List<ImageEntity> entities = apiClient.getImageArchive("apiHost", "search", "order", DESC, managerIds);
//		
//		//Then
//		assertThat(entities).containsExactly(ImageCoreApiClientTest.ime1, ImageCoreApiClientTest.ime2, ImageCoreApiClientTest.ime3, ImageCoreApiClientTest.ime4);
//		
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_getImages_with_no_nbItemPerPage_when_managerList_is_null() throws Exception {
//		//when
//		apiClient.getImageArchive("apiHost", 1, "search", "order", DESC, null);
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_getImages_with_no_nbItemPerPage_when_hostname_is_null() throws Exception {
//		//Given
//		final ArrayList<Long> managerIds = new ArrayList<Long>();
//		managerIds.add(1L);
//		
//		//when
//		apiClient.getImageArchive(null, 1, "search", "order", DESC, managerIds);
//	}
//
//	public void should_test_getImages_with_no_nbItemPerPage() throws Exception {		
//		//Given
//		final ArrayList<Long> managerIds = new ArrayList<Long>();
//		managerIds.add(1L);
//		
//		//when
//		when(apiClient.getImageArchive("apiHost", 1, null, "search", "order", DESC, managerIds)).thenReturn(listOfImage);
//		final List<ImageEntity> entities = apiClient.getImageArchive("apiHost", 1,"search", "order", DESC, managerIds);
//		
//		//Then
//		assertThat(entities).containsExactly(ImageCoreApiClientTest.ime1, ImageCoreApiClientTest.ime2, ImageCoreApiClientTest.ime3, ImageCoreApiClientTest.ime4);
//		
//	}
//	
	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_putImage_when_entity_is_null() throws Exception {
//		//when
//		apiClient.putImage("apiHost", null, 1L, null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_postImage_when_hostname_is_null() throws Exception {
//		
//		//when
//		apiClient.postImage(null, 1L, 2L, 0L, 2L, null, "toto", null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_postImage_when_managerid_is_null() throws Exception {
//		
//		//when
//		apiClient.postImage("apiHost", null, 2L, 0L, 2L, null, "toto", null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_postImage_when_managerid_is_zero() throws Exception {
//		
//		//when
//		apiClient.postImage("apiHost", 0L, 2L, 0L, 2L, null, "toto", null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_postImage_when_clientId_is_null() throws Exception {
//		
//		//when
//		apiClient.postImage("apiHost", 1L, null, 0L, 2L, null, "toto", null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_postImage_when_clientId_is_zero() throws Exception {
//		
//		//when
//		apiClient.postImage("apiHost", 1L, 0L, 0L, 2L, null, "toto", null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void should_test_postImage_when_name_is_null() throws Exception {
//		
//		//when
//		apiClient.postImage("apiHost", 1L, 0L, 0L, 2L, null, null, null);
//	}
}
