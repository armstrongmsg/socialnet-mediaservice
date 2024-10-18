package com.armstrongmsg.socialnet.mediaservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.armstrongmsg.socialnet.mediaservice.constants.ConfigurationProperties;
import com.armstrongmsg.socialnet.mediaservice.core.MediaServiceApi;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.InternalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaAlreadyExistsException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaNotFoundException;
import com.armstrongmsg.socialnet.mediaservice.util.ApplicationPaths;
import com.armstrongmsg.socialnet.mediaservice.util.MediaUtil;
import com.armstrongmsg.socialnet.mediaservice.util.PropertiesUtil;

public class IntegrationTest extends PersistenceTest {
	private static final String TEST_PICTURE_FILE_PATH_1 = ApplicationPaths.getApplicationPath() + 
			File.separator + "test-pic-1.jpg";
	private static final String TEST_PICTURE_FILE_PATH_2 = ApplicationPaths.getApplicationPath() + 
			File.separator + "test-pic-2.jpg";
	private static final String MEDIA_ID_1 = "mediaId1";
	private MockedStatic<PropertiesUtil> propertiesUtilMock;
	private PropertiesUtil propertiesUtil;
	private Map<String, String> metadata;
	private MediaUtil mediaUtils;
	
	@Before
	public void setUp() throws FatalErrorException {
		mediaUtils = Mockito.mock(MediaUtil.class);
		propertiesUtil = Mockito.mock(PropertiesUtil.class);
		Mockito.when(propertiesUtil.getProperty(ConfigurationProperties.MEDIA_STORAGE_DIRECTORY)).
			thenReturn(PersistenceTest.TEST_DIRECTORY);
		
		propertiesUtilMock = Mockito.mockStatic(PropertiesUtil.class);

		Mockito.when(PropertiesUtil.getInstance()).thenReturn(propertiesUtil);
	}
	
	@Test
	public void testApiConstructorCreatesStorageDirectory() throws FatalErrorException {
		assertFalse(new File(PersistenceTest.TEST_DIRECTORY).exists());
		
		MediaServiceApi.getInstance();
		
		assertTrue(new File(PersistenceTest.TEST_DIRECTORY).exists());
	}
	
	@Test
	public void testMediaCreateAndGet() throws IOException, FatalErrorException, MediaAlreadyExistsException, MediaNotFoundException, InternalErrorException {
		byte[] testPictureData = getTestPictureData(TEST_PICTURE_FILE_PATH_1);
		
		MediaServiceApi.getInstance().createMedia(MEDIA_ID_1, metadata, testPictureData);
		
		MediaServiceApi.reset();
		
		byte[] returnedPictureData = MediaServiceApi.getInstance().getMediaData(MEDIA_ID_1);
		
		Assert.assertArrayEquals(testPictureData, returnedPictureData);
	}
	
	@Test
	public void testCreateMediaAlreadyExists() throws IOException, FatalErrorException, MediaAlreadyExistsException, MediaNotFoundException, InternalErrorException {
		byte[] testPictureData1 = getTestPictureData(TEST_PICTURE_FILE_PATH_1);
		
		MediaServiceApi.getInstance().createMedia(MEDIA_ID_1, metadata, testPictureData1);
		
		MediaServiceApi.reset();
		
		byte[] returnedPictureData1 = MediaServiceApi.getInstance().getMediaData(MEDIA_ID_1);
		
		Assert.assertArrayEquals(testPictureData1, returnedPictureData1);
		
		byte[] testPictureData2 = getTestPictureData(TEST_PICTURE_FILE_PATH_2);
		
		try {
			MediaServiceApi.getInstance().createMedia(MEDIA_ID_1, metadata, testPictureData2);
			Assert.fail("Must throw MediaAlreadyExistsException.");
		} catch (MediaAlreadyExistsException e) {
			
		}
		
		byte[] returnedPictureData2 = MediaServiceApi.getInstance().getMediaData(MEDIA_ID_1);
		
		Assert.assertArrayEquals(testPictureData1, returnedPictureData2);
	}
	
	@Test(expected = MediaNotFoundException.class)
	public void testGetNonExistentMedia() throws IOException, FatalErrorException, MediaNotFoundException, InternalErrorException {
		MediaServiceApi.getInstance().getMediaData("invalid-id");
	}
	
	@Test(expected = InternalErrorException.class)
	public void testErrorOnMediaCreation() throws FatalErrorException, IOException, MediaAlreadyExistsException, InternalErrorException {
		byte[] testPictureData = getTestPictureData(TEST_PICTURE_FILE_PATH_1);
		
		Mockito.doThrow(IOException.class).when(mediaUtils).createMediaInStorage(MEDIA_ID_1, testPictureData);
		
		MediaServiceApi.getInstance(mediaUtils).createMedia(MEDIA_ID_1, metadata, testPictureData);
	}
	
	@Test(expected = InternalErrorException.class)
	public void testErrorOnMediaRetrieval() throws FatalErrorException, IOException, MediaAlreadyExistsException, InternalErrorException, MediaNotFoundException {
		Mockito.when(mediaUtils.mediaExists(MEDIA_ID_1)).thenReturn(true);
		Mockito.doThrow(IOException.class).when(mediaUtils).readMediaData(MEDIA_ID_1);
		
		MediaServiceApi.getInstance(mediaUtils).getMediaData(MEDIA_ID_1);
	}
	
	@After
	public void tearDown() throws IOException {
		MediaServiceApi.reset();
		
		if (propertiesUtilMock != null) {
			propertiesUtilMock.close();
		}
		
		super.tearDown();
	}
	
	private byte[] getTestPictureData(String path) throws FileNotFoundException, IOException {
		File testPictureFile = new File(path);
		FileInputStream testPictureStream = new FileInputStream(testPictureFile);
		byte[] testPictureData = new byte[] {};
		
		try {
			 testPictureData = testPictureStream.readAllBytes();
		} finally {
			testPictureStream.close();
		}
		return testPictureData;
	}
}
