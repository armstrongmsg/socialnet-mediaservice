package com.armstrongmsg.socialnet.mediaservice.exceptions;

public class InternalErrorException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InternalErrorException() {
	}
	
	public InternalErrorException(String message) {
		super(message);
	}
}
