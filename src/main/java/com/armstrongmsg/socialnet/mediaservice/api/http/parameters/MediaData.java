package com.armstrongmsg.socialnet.mediaservice.api.http.parameters;

import java.util.Map;

public class MediaData {
	private String id;
	private Map<String, String> metadata;
	private byte[] data;
	
	public String getId() {
		return id;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public byte[] getData() {
		return data;
	}
}
