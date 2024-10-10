package com.armstrongmsg.socialnet.mediaservice.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;
import com.armstrongmsg.socialnet.mediaservice.util.PropertiesUtil;

public class MediaServiceApi {
	private static MediaServiceApi instance;
	private String mediaStorageDirectory;
	
	private MediaServiceApi() throws FatalErrorException {
		// FIXME constant
		this.mediaStorageDirectory = PropertiesUtil.getInstance().getProperty("MEDIA_STORAGE_DIRECTORY");
		File storageDirectory = new File(mediaStorageDirectory);
		
		if (!storageDirectory.exists()) {
			storageDirectory.mkdir();
		}
	}
	
	public static MediaServiceApi getInstance() throws FatalErrorException {
		if (instance == null) {
			instance = new MediaServiceApi();
		}
		return instance;
	}

	public void createMedia(String id, Map<String, String> metadata, byte[] data) throws IOException {
		if (!mediaExists(id)) {
			createMediaInStorage(id, data);
		}
	}

	private boolean mediaExists(String id) {
		File mediaFile = new File(mediaStorageDirectory + "/" + id);
		return mediaFile.exists() && mediaFile.isFile();
	}
	
	private void createMediaInStorage(String id, byte[] data) throws IOException {
		File mediaFile = new File(mediaStorageDirectory + "/" + id);
		mediaFile.createNewFile();
		FileOutputStream stream = new FileOutputStream(mediaFile);
		boolean successful = false;
		
		try {
			stream.write(data);
			successful = true;
		} finally {
			stream.close();
			
			if (!successful) {
				mediaFile.delete();
			}
		}
	}

	public byte[] getMediaData(String mediaId) throws IOException {
		if (mediaExists(mediaId)) {
			byte[] mediaData = readMediaData(mediaId);
			return mediaData;
		}
		return new byte[]{};
	}

	private byte[] readMediaData(String mediaId) throws IOException {
		File mediaFile = new File(mediaStorageDirectory + "/" + mediaId);
		FileInputStream inputStream = new FileInputStream(mediaFile);
		
		try {
			return inputStream.readAllBytes();
		} finally {
			inputStream.close();
		}
	}
}
