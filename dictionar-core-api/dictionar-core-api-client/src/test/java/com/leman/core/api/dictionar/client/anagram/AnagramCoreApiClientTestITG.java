package com.leman.core.api.dictionar.client.anagram;

import java.io.IOException;

import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import org.junit.Before;
import org.junit.Test;

public class AnagramCoreApiClientTestITG {

    private static final String HOST_NAME = "local.campaigncommander.com";
    private AnagramCoreApiClient anagramCoreApiClient;

    @Before
    public void setUp() throws Exception {
        anagramCoreApiClient = new AnagramCoreApiClient("http://{0}:8080/dictionar-core", 5000, true);
    }

    //	@Test(expected=IllegalArgumentException.class)
    //	public void should_test_post_image_with_name_null() throws AnagramCoreApiException, IOException {
    //		//Given
    //
    //		File imageFile = new File(getClass().getClassLoader().getResource("image.jpg").getFile().toString());
    //		WordEntity entity = new WordEntity();
    //
    //		//When
    //		final WordEntity response = anagramCoreApiClient.postImage(HOST_NAME, 146507L, 36362L, 0L, 500000L,
    // imageFile, null, "description");
    //
    //		//Then
    //		assertThat(entity).isEqualTo(response);
    //	}
    //
    @Test
    public void should_test_get_word() throws AnagramCoreApiException, IOException {
        //Given

        WordEntity entity = new WordEntity();

        //When
        final WordEntity response = anagramCoreApiClient.getRandomWord(HOST_NAME);

        //Then
        //		assertThat(entity).isEqualTo(response);
    }

}
