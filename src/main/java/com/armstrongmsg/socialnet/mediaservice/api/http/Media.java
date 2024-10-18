package com.armstrongmsg.socialnet.mediaservice.api.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.armstrongmsg.socialnet.mediaservice.api.http.parameters.MediaData;
import com.armstrongmsg.socialnet.mediaservice.core.MediaServiceApi;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.InternalErrorException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaAlreadyExistsException;
import com.armstrongmsg.socialnet.mediaservice.exceptions.MediaNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(value = "media")
public class Media {
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Boolean> createMedia(
			@RequestBody MediaData mediaData) throws FatalErrorException, MediaAlreadyExistsException, InternalErrorException {
		MediaServiceApi.getInstance().createMedia(mediaData.getId(), mediaData.getMetadata(), mediaData.getData());
		return new ResponseEntity<Boolean>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{mediaId}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> getMedia(HttpServletResponse response,
			@PathVariable String mediaId) throws FatalErrorException, MediaNotFoundException, InternalErrorException {
		byte[] mediaData = MediaServiceApi.getInstance().getMediaData(mediaId);
		 try (InputStream inputStream = new ByteArrayInputStream(mediaData)) {
		        StreamUtils.copy(inputStream, response.getOutputStream());
		        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    } catch (IOException e) {
		        // handle
		    }
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
