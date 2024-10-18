package com.armstrongmsg.socialnet.mediaservice.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.armstrongmsg.socialnet.mediaservice.constants.ConfigurationProperties;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.InternalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaAlreadyExistsException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaNotFoundException;
import com.armstrongmsg.socialnet.mediaservice.util.MediaUtil;
import com.armstrongmsg.socialnet.mediaservice.util.PropertiesUtil;

public class MediaServiceApi {
	private static MediaServiceApi instance;
	private String mediaStorageDirectory;
	private MediaUtil mediaUtil;
	
	private MediaServiceApi() throws FatalErrorException {
		this.mediaStorageDirectory = PropertiesUtil.getInstance().
				getProperty(ConfigurationProperties.MEDIA_STORAGE_DIRECTORY);
		File storageDirectory = new File(mediaStorageDirectory);
		mediaUtil = new MediaUtil(mediaStorageDirectory);
		
		if (!storageDirectory.exists()) {
			storageDirectory.mkdir();
		}
	}
	
	private MediaServiceApi(MediaUtil mediaUtil) throws FatalErrorException {
		this.mediaUtil = mediaUtil;
		
		this.mediaStorageDirectory = PropertiesUtil.getInstance().
				getProperty(ConfigurationProperties.MEDIA_STORAGE_DIRECTORY);
		File storageDirectory = new File(mediaStorageDirectory);
		mediaUtil = new MediaUtil(mediaStorageDirectory);
		
		if (!storageDirectory.exists()) {
			storageDirectory.mkdir();
		}
	}
	
	public static MediaServiceApi getInstance(MediaUtil mediaUtils) throws FatalErrorException {
		if (instance == null) {
			instance = new MediaServiceApi(mediaUtils);
		}
		return instance;
	}
	
	public static MediaServiceApi getInstance() throws FatalErrorException {
		if (instance == null) {
			instance = new MediaServiceApi();
		}
		return instance;
	}
	
	public static void reset() {
		instance = null;
	}

	public void createMedia(String id, Map<String, String> metadata, byte[] data) throws MediaAlreadyExistsException, InternalErrorException {
		if (mediaUtil.mediaExists(id)) {
			// TODO add message
			throw new MediaAlreadyExistsException();
		} else {
			try {
				mediaUtil.createMediaInStorage(id, data);
			} catch (IOException e) {
				// TODO message
				throw new InternalErrorException();
			}
		}
	}

	public byte[] getMediaData(String mediaId) throws MediaNotFoundException, InternalErrorException {
		if (mediaUtil.mediaExists(mediaId)) {
			try {
				byte[] mediaData = mediaUtil.readMediaData(mediaId);
				return mediaData;
			} catch (IOException e) {
				// TODO message
				throw new InternalErrorException();
			}
		} else {
			// TODO message
			throw new MediaNotFoundException();
		}
	}

	public void deleteMedia(String mediaId) throws MediaNotFoundException, InternalErrorException {
		if (mediaUtil.mediaExists(mediaId)) {
			try {
				mediaUtil.deleteMedia(mediaId);
			} catch (IOException e) {
				// TODO message
				throw new InternalErrorException();
			}
		} else {
			// TODO message
			throw new MediaNotFoundException();
		}
	}
}
