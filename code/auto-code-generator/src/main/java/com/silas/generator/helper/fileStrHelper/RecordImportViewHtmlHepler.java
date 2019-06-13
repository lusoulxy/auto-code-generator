package com.silas.generator.helper.fileStrHelper;

import com.silas.generator.helper.HtmlHelper;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class RecordImportViewHtmlHepler implements CreateFileHelper,HtmlHelper{
	String HtmlZHName="导入";
	String htmlName ="importExcelView.html";

	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + "/templates/" + module + "/" + entityName.toLowerCase() + "/"+htmlName;
		// HTML<head>		
		String htmlHead = htmlHead();
		// body之前的script
		String aheadScript = aheadScript();
		// HTML<body>
		String htmlBody = htmlBody();
		// body之后的script
		String behindScript = behindScript();
		// 拼接
		fileOutputStr = htmlStart + htmlHead + aheadScript + htmlBody + behindScript;
		// 文件结尾
		fileOutputStr += n + "</html>";

		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}
	
	//导航栏
	private String topBar() {
		String topBar = n+
				"	<div class=\"top-barcenter\">\r\n" + 
				"		<ul class=\"breadcrumb top-breadcrumb\">\r\n" + 
				"			<li><i class=\"fa fa-home\"></i></li>\r\n" ; 
				if(parenModuleName!=null||!parenModuleName.equals("")) {
					topBar+="			<li>"+parenModuleName+"</li>\r\n";
				}
				if(moduleName!=null||!moduleName.equals("")) {
					topBar+="			<li>"+moduleName+"</li>\r\n";
				}
		topBar+=
				"			<li><a href=\"/"+entityName+"/list\">"+moduleName+HtmlZHName+"</a></li>\r\n" + 
				"		</ul>\r\n" + 
				"		<ul class=\"top-toolbar\"></ul>\r\n" + 
				"	</div>";
		return topBar;
	}

	// htmlBody
	private String htmlBody() {
		String str = n + 
				"<body>\r\n" ; 
		//top-barcenter导航栏
		String topBar = topBar();
		str += topBar+n;
		str += 	"	<div align=\"center\">\r\n" + 
				"		<form class=\"form-horizontal\" id=\"form_table\" action=\"#\"\r\n" + 
				"			enctype=\"multipart/form-data\" method=\"post\">\r\n" + 
				"			<br /> <br /> <input type=\"button\" class=\"btn btn-primary\"\r\n" + 
				"				value=\"导入\" onclick=\"ajaxFileUpload()\">\r\n" + 
				"			<!-- <button type=\"submit\" class=\"btn btn-primary\">导入</button> -->\r\n" + 
				"			&nbsp; <input type=\"reset\" value=\"重置\"> <input\r\n" + 
				"				class=\"form-input\" type=\"file\" name=\"filename\" id=\"filename\"></input>\r\n" + 
				"		</form>\r\n" + 
				"	</div>\r\n" + 
				"	<div align=\"center\">\r\n" + 
				"		<textarea id=\"result_text\" hidden=\"true\" style=\"width:80%;height:200px\"></textarea>\r\n" + 
				"	</div>\r\n" + 
				"</body>";
		return str;
	}
	

	// body之后的script
	private String behindScript() {
		String str =n+
				"<script type=\"text/javascript\">\r\n" + 
				"    function ajaxFileUpload() {\r\n" + 
				"   	 $(\"#result_text\").attr(\"hidden\",false);\r\n" + 
				"   	 $(\"#result_text\").val(\"等待返回结果......\");\r\n" + 
				"        $.ajaxFileUpload\r\n" + 
				"        (\r\n" + 
				"            {\r\n" + 
				"                url: '/"+entityName+"/importExcel', //用于文件上传的服务器端请求地址\r\n" + 
				"                secureuri: false, //是否需要安全协议，一般设置为false\r\n" + 
				"                fileElementId: 'filename', //文件上传域的ID\r\n" + 
				"                dataType: 'String', //返回值类型 一般设置为json\r\n" + 
				"                success: function (data, status)  //服务器成功响应处理函数\r\n" + 
				"                {\r\n" + 
				"               	 //alert(data);\r\n" + 
				"               	 $(\"#result_text\").val(data);\r\n" + 
				"                },\r\n" + 
				"                error: function (data, status, e)//服务器响应失败处理函数\r\n" + 
				"                {\r\n" + 
				"               	 console.log(e)\r\n" + 
				"               	 $(\"#result_text\").val(data);\r\n" + 
				"                }\r\n" + 
				"            }\r\n" + 
				"        )\r\n" + 
				"        return false;\r\n" + 
				"    }\r\n" + 
				"</script>";
		return str;
	}

	// body之前的script
	private String aheadScript() {
		String str ="";
		return str;
	}

	// HTML<head>
	private String htmlHead() {
		String str = n + 
				"<head>\r\n" + 
				"	<meta charset=\"utf-8\" />\r\n" + 
				"	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n" + 
				"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n" + 
				"	<script th:src=\"@{/Content/assets/lib/jquery-2.1.1.min.js}\"></script>\r\n" + 
				"	<script th:src=\"@{/Content/assets/lib/ajaxfileupload.js}\"></script>\r\n" + 
				"	<script th:src=\"@{/Content/assets/lib/aYin/aYin.js}\"></script>\r\n" + 
				"	<script th:src=\"@{/Content/assets/lib/bootstrap/js/bootstrap.min.js}\"></script>\r\n" + 
				"	<link th:href=\"@{/Content/assets/css/main.css}\" rel=\"stylesheet\" />\r\n" + 
				"	<link th:href=\"@{/Content/assets/lib/fontawesome-pro/css/fontawesome.css}\" rel=\"stylesheet\" />\r\n" + 
				"	<link th:href=\"@{/Content/assets/lib/bootstrap/css/bootstrap.min.css}\" rel=\"stylesheet\" />\r\n" + 
				"	<link th:href=\"@{/Content/assets/lib/aYin/aYin.css}\" rel=\"stylesheet\" />\r\n" + 
				"</head>";
		return str;
	}

}
