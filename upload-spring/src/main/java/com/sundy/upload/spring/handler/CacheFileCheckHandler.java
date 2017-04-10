package com.sundy.upload.spring.handler;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.sundy.upload.spring.contant.ContantConfig;
import com.sundy.upload.spring.message.RspMessage;
import com.sundy.upload.spring.util.FileUtil;


public class CacheFileCheckHandler implements UploaderHandler {
	
	public String getPath() {
		return ContantConfig.instance.chunkCheckPath;
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		//body true 表示不需上传
		RspMessage rspMessage = new RspMessage(true, "文件已存在");
		
		String fileName = request.getParameter("fileName");
		String md5 = request.getParameter("md5");
		String fileCacheName = request.getParameter("fileCacheName")==null?null:request.getParameter("fileCacheName");
		long fileCacheSize = request.getParameter("fileCacheSize")==null?0l:Long.parseLong(request.getParameter("fileCacheSize"));
		
		String base = FileUtil.getBase();
		if(fileName==null){
			rspMessage.setIsSuccess(false);
			rspMessage.setMessage("上传文件名参数空");
			response.getWriter().write(JSON.toJSONString(rspMessage));
			return ;
		}
		
		if(fileCacheName==null){
			rspMessage.setIsSuccess(false);
			rspMessage.setMessage("上传缓存文件名参数空");
			response.getWriter().write(JSON.toJSONString(rspMessage));
			return ;
		}
		
		if(fileCacheSize==0l){
			rspMessage.setIsSuccess(false);
			rspMessage.setMessage("上传缓存文件长度参数为空");
			response.getWriter().write(JSON.toJSONString(rspMessage));
			return ;
		}
		
		String filePath = base+ContantConfig.fileSeparator+md5;
		if(!FileUtil.exist(filePath)){
			rspMessage.setIsSuccess(false);
			rspMessage.setMessage(fileName+" 文件 "+fileCacheName+"还没开始上传");
			response.getWriter().write(JSON.toJSONString(rspMessage));
			return ;
		}
		
		String cacheFilePath = base+ContantConfig.fileSeparator+md5+ContantConfig.fileSeparator+fileCacheName;
		if(!FileUtil.exist(cacheFilePath)){
			rspMessage.setIsSuccess(false);
			rspMessage.setMessage(fileName+" 文件 "+fileCacheName+"还没开始上传");
			response.getWriter().write(JSON.toJSONString(rspMessage, true));
			return ;
		}else{
			File file = new File(cacheFilePath);
			long currentLength = file.length();
			if(currentLength==fileCacheSize){
				rspMessage.setIsSuccess(true);
				rspMessage.setMessage("文件已存在，不需再次上传");
				response.getWriter().write(JSON.toJSONString(rspMessage, true));
				return ;
			}else{
				file.delete();
				rspMessage.setIsSuccess(false);
				rspMessage.setMessage("文件已存在，但长度不一致，需重新上传");
				response.getWriter().write(JSON.toJSONString(rspMessage, true));
				return ;
			}
		}
	}
	
}
