package com.sundy.upload.spring.config.configuration;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;


/**
 * 用于处理attribute的properties属性
 * @author Administrator
 *
 */
public class UploadScannerConfigure implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;
	private boolean processPropertyPlaceHolders;
	
	public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
	    this.processPropertyPlaceHolders = processPropertyPlaceHolders;
	  }
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		if (this.processPropertyPlaceHolders) {
			processPropertyPlaceHolders();
		}

	}
	
	private void processPropertyPlaceHolders() {
	    if (applicationContext instanceof ConfigurableApplicationContext) {
//	      BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory().getBeanDefinition(beanName);
//	      DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//	      factory.registerBeanDefinition(beanName, mapperScannerBean);
	    }
	  }

}
