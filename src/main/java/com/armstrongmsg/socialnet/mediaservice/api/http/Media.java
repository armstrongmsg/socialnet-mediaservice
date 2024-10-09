package com.armstrongmsg.socialnet.mediaservice.api.http;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.armstrongmsg.socialnet.mediaservice.api.http.parameters.MediaData;
import com.armstrongmsg.socialnet.mediaservice.core.MediaServiceApi;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;

@CrossOrigin
@RestController
@RequestMapping(value = "media")
public class Media {
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Boolean> createMedia(
			@RequestBody MediaData mediaData) throws IOException, FatalErrorException {
		MediaServiceApi.getInstance().createMedia(mediaData.getId(), mediaData.getMetadata(), mediaData.getData());
		return new ResponseEntity<Boolean>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{mediaId}", method = RequestMethod.GET)
	public ResponseEntity<String> getMedia(
			@PathVariable String mediaId) throws IOException, FatalErrorException {
		String mediaData = MediaServiceApi.getInstance().getMediaData(mediaId);
		return new ResponseEntity<String>(mediaData, HttpStatus.OK);
	}
}
