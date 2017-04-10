package com.sundy.upload.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.w3c.dom.Element;

import com.sundy.upload.spring.config.configuration.UploadConfigurationScanner;
import com.sundy.upload.spring.handler.UploadHandlerAdapter;
import com.sundy.upload.spring.handlerMapper.UploadHandlerMapper;

public class UploadConfigureBeanDefinitionParser implements BeanDefinitionParser {

	public static final String HANDLER_MAPPING_BEAN_NAME = UploadHandlerMapper.class.getName();
	
	public static final String HANDLER_ADAPTER_BEAN_NAME = UploadHandlerAdapter.class.getName();
	
	private static final String ATTRIBUTE_CHECK_PATH = "check-path";
	private static final String ATTRIBUTE_CHUNK_CHECK_PATH = "chunk-check-path";
	private static final String ATTRIBUTE_MERGE_PATH = "merge-path";
	private static final String ATTRIBUTE_UPLOAD_PATH = "upload-path";
	private static final String ATTRIBUTE_STORE_PATH = "store-path";
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		UploadConfigurationScanner scanner = new UploadConfigurationScanner(parserContext.getRegistry());
		XmlReaderContext readerContext = parserContext.getReaderContext();
		scanner.setResourceLoader(readerContext.getResourceLoader());
		try {
			String checkPath = element.getAttribute(ATTRIBUTE_CHECK_PATH);
			if(StringUtils.hasText(checkPath)){
				scanner.setCheckPath(checkPath);
			}else{
				throw new RuntimeException("请配置文件检测访问路径");
			}
			
			String chunkCheckPath = element.getAttribute(ATTRIBUTE_CHUNK_CHECK_PATH);
			if(StringUtils.hasText(chunkCheckPath)){
				scanner.setChunkCheckPath(chunkCheckPath);
			}else{
				throw new RuntimeException("请配置文件块检测访问路径");
			}
			
			String mergePath = element.getAttribute(ATTRIBUTE_MERGE_PATH);
			if(StringUtils.hasText(mergePath)){
				scanner.setMergePath(mergePath);
			}else{
				throw new RuntimeException("请配置文件块合并访问路径");
			}
			
			String uploadPath = element.getAttribute(ATTRIBUTE_UPLOAD_PATH);
			if(StringUtils.hasText(uploadPath)){
				scanner.setUploadPath(uploadPath);
			}else{
				throw new RuntimeException("请配置文件块上传访问路径");
			}
			
			String storePath = element.getAttribute(ATTRIBUTE_STORE_PATH);
			if(StringUtils.hasText(storePath)){
				scanner.setStorePath(storePath);
			}else{
				throw new RuntimeException("请配置上传文件存储路径");
			}
		} catch (Exception e) {
			 readerContext.error(e.getMessage(), readerContext.extractSource(element), e.getCause());
		}
		
		RootBeanDefinition handlerMappingDef = new RootBeanDefinition(UploadHandlerMapper.class);
		handlerMappingDef.setSource(parserContext.extractSource(element));
		handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		readerContext.getRegistry().registerBeanDefinition(HANDLER_MAPPING_BEAN_NAME, handlerMappingDef);
		
		
		RootBeanDefinition handlerAdapterDef = new RootBeanDefinition(UploadHandlerAdapter.class);
		handlerAdapterDef.setSource(parserContext.extractSource(element));
		handlerAdapterDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		readerContext.getRegistry().registerBeanDefinition(HANDLER_ADAPTER_BEAN_NAME, handlerAdapterDef);
		
		
		
		return null;
	}

}
