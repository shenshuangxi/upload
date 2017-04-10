package com.sundy.upload.spring.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UploaderHandler {

	public String getPath();
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException ;
	
}
