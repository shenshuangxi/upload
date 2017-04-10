package com.sundy.upload.spring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

public class UploadHandlerAdapter implements HandlerAdapter {

	public boolean supports(Object handler) {
		return (handler instanceof UploaderHandler);
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		((UploaderHandler)handler).handleRequest(request, response);
		return null;
	}

	public long getLastModified(HttpServletRequest request, Object handler) {
		return -1L;
	}

}
