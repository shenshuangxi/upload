package com.sundy.upload.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("upload", new UploadConfigureBeanDefinitionParser());
	}

}
