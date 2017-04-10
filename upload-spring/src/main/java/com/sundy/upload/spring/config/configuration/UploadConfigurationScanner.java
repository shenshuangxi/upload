package com.sundy.upload.spring.config.configuration;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import com.sundy.upload.spring.contant.ContantConfig;

public class UploadConfigurationScanner extends ClassPathBeanDefinitionScanner {

	public UploadConfigurationScanner(BeanDefinitionRegistry registry) {
		super(registry,false);
	}

	public void setCheckPath(String checkPath) {
		ContantConfig.instance.checkPath = checkPath;
	}

	public void setChunkCheckPath(String chunkCheckPath) {
		ContantConfig.instance.chunkCheckPath = chunkCheckPath;
	}

	public void setMergePath(String mergePath) {
		ContantConfig.instance.mergePath = mergePath;
	}

	public void setUploadPath(String uploadPath) {
		ContantConfig.instance.uploadPath = uploadPath;
	}

	public void setStorePath(String storePath) {
		ContantConfig.instance.storePath = storePath;
	}

	

	
}
