package com.armstrongmsg.socialnet.mediaservice.exceptions;

public class MediaAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public MediaAlreadyExistsException() {
		
	}
	
	public MediaAlreadyExistsException(String message) {
		super(message);
	}
}
