package com.armstrongmsg.socialnet.mediaservice.api.http.parameters;

import java.util.Map;

public class MediaData {
	private String id;
	private Map<String, String> metadata;
	private String data;
	
	public String getId() {
		return id;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public String getData() {
		return data;
	}
}
