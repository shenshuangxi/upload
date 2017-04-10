package com.sundy.upload.spring.contant;

import java.io.File;

public class ContantConfig {

	public final static String fileSeparator = File.separator;
	public final static String md5File = "fileMd5.properties";
//	
//	public final static String winDirPath = "d:\\uploadFiles";
//	
//	public final static String linuxDirPath = "/usr/local/src/upload";
//	
//	
	
	public String checkPath;
	public String chunkCheckPath;
	public String mergePath;
	public String uploadPath;
	public String storePath;
	
	public static ContantConfig instance = new ContantConfig();
	
	private ContantConfig(){}
	
	
	
	
	
	
}
