package com.armstrongmsg.socialnet.mediaservice.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.armstrongmsg.socialnet.mediaservice.constants.ConfigurationProperties;
import com.armstrongmsg.socialnet.mediaservice.constants.Messages;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.InternalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaAlreadyExistsException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaNotFoundException;
import com.armstrongmsg.socialnet.mediaservice.util.MediaUtil;
import com.armstrongmsg.socialnet.mediaservice.util.PropertiesUtil;

public class MediaServiceApi {
	private static MediaServiceApi instance;
	private static Logger logger = LoggerFactory.getLogger(MediaServiceApi.class);
	
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

	public void createMedia(String id, Map<String, String> metadata, byte[] data) 
			throws MediaAlreadyExistsException, InternalErrorException {
		logger.debug(Messages.Logging.RECEIVED_CREATE_MEDIA_REQUEST, id);
		
		if (mediaUtil.mediaExists(id)) {
			throw new MediaAlreadyExistsException(
					String.format(Messages.Exception.MEDIA_ALREADY_EXISTS, id));
		} else {
			try {
				mediaUtil.createMediaInStorage(id, data);
			} catch (IOException e) {
				throw new InternalErrorException(
						String.format(Messages.Exception.ERROR_ON_CREATING_MEDIA, id));
			}
		}
	}

	public byte[] getMediaData(String mediaId) throws MediaNotFoundException, InternalErrorException {
		logger.debug(Messages.Logging.RECEIVED_GET_MEDIA_REQUEST, mediaId);
		
		if (mediaUtil.mediaExists(mediaId)) {
			try {
				byte[] mediaData = mediaUtil.readMediaData(mediaId);
				return mediaData;
			} catch (IOException e) {
				throw new InternalErrorException(
						String.format(Messages.Exception.ERROR_ON_READING_MEDIA, mediaId));
			}
		} else {
			throw new MediaNotFoundException(String.format(Messages.Exception.MEDIA_NOT_FOUND, mediaId));
		}
	}

	public void deleteMedia(String mediaId) throws MediaNotFoundException, InternalErrorException {
		logger.debug(Messages.Logging.RECEIVED_DELETE_MEDIA_REQUEST, mediaId);
		
		if (mediaUtil.mediaExists(mediaId)) {
			try {
				mediaUtil.deleteMedia(mediaId);
			} catch (IOException e) {
				throw new InternalErrorException(String.format(Messages.Exception.ERROR_ON_DELETING_MEDIA, mediaId));
			}
		} else {
			throw new MediaNotFoundException(String.format(Messages.Exception.MEDIA_NOT_FOUND, mediaId));
		}
	}
}
