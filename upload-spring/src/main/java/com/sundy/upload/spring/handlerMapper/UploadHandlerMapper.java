package com.sundy.upload.spring.handlerMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.util.UrlPathHelper;

import com.sundy.upload.spring.handler.CacheFileCheckHandler;
import com.sundy.upload.spring.handler.FileCheckHandler;
import com.sundy.upload.spring.handler.FileMergeHandler;
import com.sundy.upload.spring.handler.FileUploadHandler;
import com.sundy.upload.spring.handler.UploaderHandler;

public class UploadHandlerMapper implements HandlerMapping {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	private final List<HandlerInterceptor> adaptedInterceptors = new ArrayList<HandlerInterceptor>();
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	private  Map<String,UploaderHandler> handlerMapper = new HashMap<String, UploaderHandler>(); 
	
	private static List<UploaderHandler> handlerList = new ArrayList<UploaderHandler>();
	
	static{
		handlerList.add(new CacheFileCheckHandler());
		handlerList.add(new FileCheckHandler());
		handlerList.add(new FileMergeHandler());
		handlerList.add(new FileUploadHandler());
	}
	
	
	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		HandlerExecutionChain handlerExecutionChain = null;
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		UploaderHandler handler = this.handlerMapper.get(lookupPath);
		if(handler!=null){
			handlerExecutionChain = getHandlerExecutionChain(handler, request);
		}else{
			for(UploaderHandler uploaderHandler : handlerList){
				String path = uploaderHandler.getPath();
				if(lookupPath.equals(path)){
					handlerExecutionChain = getHandlerExecutionChain(uploaderHandler, request);
					this.handlerMapper.put(lookupPath, uploaderHandler);
					break;
				}
			}
		}
		return handlerExecutionChain;
	}
	
	
	private HandlerExecutionChain getHandlerExecutionChain(Object handler, HttpServletRequest request) {
		HandlerExecutionChain chain = (handler instanceof HandlerExecutionChain ?
				(HandlerExecutionChain) handler : new HandlerExecutionChain(handler));
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		for (HandlerInterceptor interceptor : this.adaptedInterceptors) {
			if (interceptor instanceof MappedInterceptor) {
				MappedInterceptor mappedInterceptor = (MappedInterceptor) interceptor;
				if (mappedInterceptor.matches(lookupPath, this.pathMatcher)) {
					chain.addInterceptor(mappedInterceptor.getInterceptor());
				}
			}
			else {
				chain.addInterceptor(interceptor);
			}
		}
		return chain;
	}

}
