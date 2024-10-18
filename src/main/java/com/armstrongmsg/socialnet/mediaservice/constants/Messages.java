package com.armstrongmsg.socialnet.mediaservice.constants;

public class Messages {
	public static class Logging {
		public static final String COULD_NOT_LOAD_ADMIN_CONFIGURATION = "Could not load admin configuration. Message: {}.";
		public static final String RECEIVED_CREATE_MEDIA_REQUEST = "Received create media request. Id:{}.";
		public static final String RECEIVED_DELETE_MEDIA_REQUEST = "Received delete media request. Id:{}.";
		public static final String RECEIVED_GET_MEDIA_REQUEST = "Received get media request. Id:{}.";
	}
	
	public static class Exception {
		public static final String COULD_NOT_LOAD_ADMIN_CONFIGURATION = "Could not load admin configuration. Message: %s.";
		public static final String ERROR_ON_CREATING_MEDIA = "Error on creating media. Id:%s.";
		public static final String ERROR_ON_DELETING_MEDIA = "Error on deleting media. Id:%s.";
		public static final String ERROR_ON_READING_MEDIA = "Error on reading media. Id:%s.";
		public static final String MEDIA_ALREADY_EXISTS = "Media already exists. Id:%s.";
		public static final String MEDIA_NOT_FOUND = "Media not found. Id:%s.";
	}
}
