package com.armstrongmsg.socialnet.mediaservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.armstrongmsg.socialnet.mediaservice.core.MediaServiceApi;
import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;

@Component
public class Main implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws FatalErrorException {
    	MediaServiceApi.getInstance();
    }
}
