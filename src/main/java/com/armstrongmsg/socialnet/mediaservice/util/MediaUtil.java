package com.armstrongmsg.socialnet.mediaservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MediaUtil {
	private String mediaStorageDirectory;
	
	public MediaUtil(String mediaStorageDirectory) {
		this.mediaStorageDirectory = mediaStorageDirectory;
	}

	public boolean mediaExists(String id) {
		File mediaFile = new File(mediaStorageDirectory + File.separator + id);
		return mediaFile.exists() && mediaFile.isFile();
	}
	
	public void createMediaInStorage(String id, byte[] data) throws IOException {
		File mediaFile = new File(mediaStorageDirectory + File.separator + id);
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
	
	public byte[] readMediaData(String mediaId) throws IOException {
		File mediaFile = new File(mediaStorageDirectory + File.separator + mediaId);
		FileInputStream inputStream = new FileInputStream(mediaFile);
		
		try {
			return inputStream.readAllBytes();
		} finally {
			inputStream.close();
		}
	}
}
