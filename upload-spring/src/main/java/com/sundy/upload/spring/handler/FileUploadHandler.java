package com.sundy.upload.spring.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.sundy.upload.spring.contant.ContantConfig;
import com.sundy.upload.spring.message.RspMessage;
import com.sundy.upload.spring.util.FileUtil;


public class FileUploadHandler implements UploaderHandler {

	public String getPath() {
		return ContantConfig.instance.uploadPath;
	}
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		RspMessage rspMessage = new RspMessage(true, "上传成功", true);
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10*1024*1024);
		// 设置临时文件存储位置
		String base = FileUtil.getBase();
		File file = new File(base);
		if(!file.exists())
			file.mkdirs();
		factory.setRepository(file);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(10*1024*1024*1024l);
		// 设置整个request的最大值
		upload.setSizeMax(10*1024*1024*1024l);
		upload.setHeaderEncoding("UTF-8");
		File fileDir = null;
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);
			String fileMd5 = null;  
	        String chunkname = null;
	        FileItem item = null;
			for(FileItem tempItem : items){
				if(tempItem.isFormField()){  
	                String fieldName = tempItem.getFieldName();  
	                if(fieldName.equals("fileMd5")){  
	                    fileMd5 = tempItem.getString("utf-8");  
	                }  
	                if(fieldName.equals("chunk")){  
	                	chunkname = tempItem.getString("utf-8");  
	                }
	            }else{
	            	item = tempItem;
	            } 
			}
			if(fileMd5!=null&&item!=null&&chunkname!=null){
				fileDir = new File(base+File.separator+fileMd5);  
                if(!fileDir.exists()){  
                	fileDir.mkdir();  
                }  
                System.out.println(fileMd5+File.separator+chunkname);
                File chunkFile = new File(base+File.separator+fileMd5+File.separator+chunkname);  
                item.write(chunkFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rspMessage.setIsSuccess(false);
			rspMessage.setMessage(e.getMessage());
			rspMessage.setBody(e);
		}
		response.getWriter().write(JSON.toJSONString(rspMessage));
	}	
}
