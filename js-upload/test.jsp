<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	StringBuffer uploadUrl = new StringBuffer("http://");
	uploadUrl.append(request.getHeader("Host"));
	uploadUrl.append(request.getContextPath());
	uploadUrl.append("/FileUploadServlet.htm1");
	System.out.println("uploadUrl====>"+uploadUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分割大文件上传</title>
</head>
<body>
<body>
		<div  id="fileSelect" >文件选择...</div>
		<button onclick="buttonInit();" >flash按钮初始化</button>
		<script type="text/javascript">
			function textSize(text) {
			    var span = document.createElement("span");
			    var result = {};
			    result.width = span.offsetWidth;
			    result.height = span.offsetWidth; 
			    span.style.visibility = "hidden";
			    document.body.appendChild(span);
			    if (typeof span.textContent != "undefined")
			        span.textContent = text;
			    else span.innerText = text;
			    result.width = span.offsetWidth - result.width;
			    result.height = span.offsetHeight - result.height;
			    span.parentNode.removeChild(span);
			    return result;
			}
			var fileSelectDom = document.getElementById("fileSelect");
			var buttonTitle = fileSelectDom.innerHTML;
			var size = textSize(buttonTitle);
			fileSelectDom.style.zindex = -1;
			fileSelectDom.style.overflow = "hidden";
			fileSelectDom.style.position = "relative";
			//insert flash object
			var html = 	'<object style="position: absolute;left: 0;top: 0;" align="center"  id="GnifSelectFiles" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%">'+
							'<param name="movie" value="${basePath}js/GnifSelectFiles.swf" />'+
							'<param name="wmode" value="transparent" />'+
							'<param name="allowscriptaccess" value="always" />'+
							'<embed style="position: absolute;left: 0;top: 0;" wmode="transparent" quality="high"  align="center" id="GnifSelectFiles" src="${basePath}js/GnifSelectFiles.swf" type="application/x-shockwave-flash" width="100%" height="100%">'+
							'</embed>'+
						'</object>'
						
			fileSelectDom.innerHTML = buttonTitle+html;
			fileSelectDom.style.width=size.width+"px";
			var GnifSelectFiles;
			if(navigator.appName.indexOf("Microsoft") != -1){
				 GnifSelectFiles = document.getElementById('GnifSelectFiles');
			}else{
				 GnifSelectFiles = document.embeds['GnifSelectFiles'];
			}
			try{
				//GnifSelectFiles.test(); //用于测试flash是否可用
			}catch (e) {
				alert(e);
				fileSelectDom.style.width="100%";
				fileSelectDom.innerHTML =   '<p>' + 
												'Either scripts and active content are not permitted to run or Adobe Flash Player version 10.0.0 or greater is not installed.' + 
											'</p>' + 
											'<a href="http://www.adobe.com/go/getflashplayer">' + 
												'<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />' + 
											'</a>';
			}
			function buttonInit(){
				try{
					GnifSelectFiles.gnif_initSelectButton(true);
				}catch (e) {
					alert(e);
				}
			}
		</script>
   </body>

</body>
</html>