package com.armstrongmsg.socialnet.mediaservice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.armstrongmsg.socialnet.mediaservice.exceptions.FatalErrorException;

public class PropertiesUtil {
	private static PropertiesUtil instance;
	
	private Properties properties;
	
	public static PropertiesUtil getInstance() throws FatalErrorException {
		if (instance == null) {
			try {
				instance = new PropertiesUtil();
			} catch (FileNotFoundException e) {
				// TODO add message
				throw new FatalErrorException();
			} catch (IOException e) {
				// TODO add message
				throw new FatalErrorException();
			}
		}
		
		return instance;
	}
	
	private PropertiesUtil() throws FileNotFoundException, IOException {
		properties = new Properties();
		String configurationPath = ApplicationPaths.getApplicationPropertiesPath();
		properties.load(new FileInputStream(configurationPath));
	}
	
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	public String getProperty(String propertyName, String propertyDefaultValue) {
		return properties.getProperty(propertyName, propertyDefaultValue);
	}
}