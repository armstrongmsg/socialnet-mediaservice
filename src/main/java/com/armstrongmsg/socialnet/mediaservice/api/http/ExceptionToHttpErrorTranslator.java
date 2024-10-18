package com.armstrongmsg.socialnet.mediaservice.api.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.armstrongmsg.socialnet.mediaservice.api.http.response.ExceptionResponse;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.InternalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaAlreadyExistsException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaNotFoundException;

@ControllerAdvice
public class ExceptionToHttpErrorTranslator extends ResponseEntityExceptionHandler {
	@ExceptionHandler(MediaNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleMediaNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse details = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MediaAlreadyExistsException.class)
	public final ResponseEntity<ExceptionResponse> handleMediaAlreadyExistsException(Exception ex, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InternalErrorException.class)
	public final ResponseEntity<ExceptionResponse> handleInternalErrorException(Exception ex, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FatalErrorException.class)
	public final ResponseEntity<ExceptionResponse> handleFatalErrorException(Exception ex, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
