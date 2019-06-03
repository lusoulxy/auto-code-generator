package com.silas.generator.helper.fileStrHelper;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class RecordListHtmlHepler implements CreateFileHelper,HtmlHelper{
	
	int listLimit = 0;
	{
		if(colsLimit>colList.size())
			this.listLimit=colList.size();
		else if(colsLimit!=0){
			listLimit=colsLimit;
		}
	}
	
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + "/templates/" +module+"/"+ entityName.toLowerCase() + "/list.html";
		//HTML<head>
		String htmlHead = htmlHead();
		//body之前的script
		String aheadScript = aheadScript();
		//HTML<body>
		String htmlBody = htmlBody();
		//body之后的script
		String behindScript = behindScript();
		//拼接
		fileOutputStr=htmlStart + htmlHead + aheadScript + htmlBody + behindScript;
		//文件结尾
		fileOutputStr += n+"</html>";
		
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}
	//HTML<body>
	private String htmlBody() {
		String str = n+"<body>";
		//top-barcenter导航栏
		String topBar = n+
				"	<div class=\"top-barcenter\">\r\n" + 
				"		<ul class=\"breadcrumb top-breadcrumb\">\r\n" + 
				"			<li><i class=\"fa fa-home\"></i></li>\r\n" + 
				"			<li>"+moduleName+"</li>\r\n" + 
				"			<li><a href=\"/"+entityName+"/list\">"+moduleName+"列表</a></li>\r\n" + 
				"		</ul>\r\n" + 
				"		<ul class=\"top-toolbar\"></ul>\r\n" + 
				"	</div>";
		//div main-wrap主体内容
		String mainDiv = n+
				"	<div class=\"main-wrap\">\r\n" ;
		//toolbar-wrap search_form 工具栏-搜索
		String searchBar = searchBar();
		//toolbar-wrap navbar-form 工具栏（新增、导入、导出、模板下载等）
		String toolBar = toolBar();
		String table = n+tab2+"<table class=\"table table-hover table-striped \">";
		//table-thead
		String tableHead = tableHead();
		//table-tbody
		String tableBody = tableBody();
		
		table+=tableHead+tableBody+n+tab2+"</table>";
		//div-pagination
		String pagination= pagination();
		mainDiv+=n+searchBar+toolBar+table+pagination+tab+"</div>";
		str+=topBar+mainDiv+"</body>";
		return str;
	}
	//
	public String toolBar() {
		String str = n+
				"		<div class=\"toolbar-wrap\">\r\n" + 
				"			<div class=\" navbar-form navbar-left\">\r\n" + 
				"				<a href=\"/"+entityName+"/add\">\r\n" + 
				"					<button class=\"btn btn-default\">\r\n" + 
				"						<span class=\"fa fa-file\"></span> 新建\r\n" + 
				"					</button>\r\n" + 
				"				</a>\r\n" + 
				"			</div>\r\n" + 
				"		</div>";
		return str;
	}

	//
	public String tableHead() {
		String str = n+
				"			<thead>\r\n" + 
				"				<tr align=\"center\">\r\n" + 
				"					<th width=\"100px\">序号</th>\r\n" ;
		String ths="";
		for(int i=0;i<listLimit;i++) {
			String colZHName = colList.get(i).getRemark();//列中文名，待完善 TODO
			ths += n+"					<th>"+colZHName+"</th>";
		}
		
		//限制的以注释形式生成
		ths +=n+"					<!-- "+n;
		for(int i=listLimit;i<colList.size();i++) {
			String colZHName = colList.get(i).getRemark();//列中文名，待完善 TODO
			ths += n+"					<th>"+colZHName+"</th>";
		}
		ths +=n+"					 -->"+n;
		
//		for(Column column:colList) {
//			String colZHName = column.getRemark();//列中文名，待完善 TODO
//			ths += n+"					<th>"+colZHName+"</th>";
//		}
		str += n+ths+
				"					<th style=\"width:100px\">操作</th>\r\n"+
				"				</tr>\r\n" + 
				"			</thead>";
		return str;
	}

	//
	public String pagination() {
		String str =n+
				"		<div align=\"right\">\r\n" + 
				"			<ul class=\"pagination\">\r\n" + 
				"				<li><a href=\"javascript:void(0)\" onclick=\"firstPage()\">首页</a></li>\r\n" + 
				"				<li><a href=\"javascript:void(0)\" onclick=\"prePage()\">上一页</a></li>\r\n" + 
				"				<li><a href=\"javascript:void(0)\" onclick=\"nextPage()\">下一页</a></li>\r\n" + 
				"				<li><a href=\"javascript:void(0)\" onclick=\"lastPage()\">尾页</a></li>\r\n" + 
				"				<li>&ensp;\r\n" + 
				"					<td th:text=\"${pageNum}\"></td>/\r\n" + 
				"					<td th:text=\"${TotalPages}\"></td>&ensp;页\r\n" + 
				"				</li>\r\n" + 
				"				<li>总&ensp;\r\n" + 
				"					<td th:text=\"${allnum}\"></td>&ensp;条&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;\r\n" + 
				"				</li>\r\n" + 
				"			</ul>\r\n" + 
				"		</div>";
		return str;
	}

	//
	public String tableBody() {
		String str =n+
				"			<tbody>\r\n" + 
				"				<tr align=\"center\" th:each=\""+recordName+","+recordName+"start : ${"+recordName+"List}\">\r\n";
		String tds =
				"					<td th:text=\"${"+recordName+"start.count} + (${pageNum} -1)*${size} \"></td>\r\n";
		for(int i=0;i<listLimit;i++) {
			Column column = colList.get(i);
			String colName = column.getEntityField();
			tds +="					<!-- "+column.getRemark()+" -->"+n;
			tds +="					<td th:title=\"${"+recordName+"."+colName+"}\" th:text=\"${"+recordName+"."+colName+"}\"></td>\r\n" ;
		}
		
		//限制的列以注释形式生成
		tds +="					<!-- "+n;
		for(int i=listLimit;i<colList.size();i++) {
			Column column = colList.get(i);
			String colName = column.getEntityField();
			tds +="					"+column.getRemark()+n;
			tds +="					<td th:title=\"${"+recordName+"."+colName+"}\" th:text=\"${"+recordName+"."+colName+"}\"></td>\r\n" ;
		}
		tds +="					 -->"+n;
//		for(Column column : colList) {
//			String colName = column.getEntityField();
//			tds +="					<!-- "+column.getRemark()+" -->";
//			tds +="					<td th:title=\"${"+recordName+"."+colName+"}\" th:text=\"${"+recordName+"."+colName+"}\"></td>\r\n" ;
//		}
		tds +=n+
				"					<td><a th:href=\"@{'/"+entityName+"/updateview/' + ${"+recordName+"."+primary_col.getEntityField()+"}}\"><i\r\n" + 
				"							class=\"fa fa-edit fa-button\"></i></a> <a href=\"javascript:void(0)\"\r\n" + 
				"						th:onclick=\"deleteitems([[${"+recordName+"."+primary_col.getEntityField()+"}]]);\"><i\r\n" + 
				"							class=\"fa fa-trash-alt fa-button\"></i></a></td>\r\n" ;
		str+=tds+
				"				</tr>\r\n" + 
				"			</tbody>";
		return str;
	}

	//
	public String searchBar() {
		String str =n+
				"		<div class=\"toolbar-wrap\">\r\n" + 
				"			<form id=\"search_form\" role=\"search\"\r\n" + 
				"				method=\"get\">\r\n" + 
				"				<div class=\"search-box\">\r\n" + 
				"					<div class=\"row search-input\">\r\n" ;
		String search_input ="";
	
		if(colList!=null&&colList.size()>0) {
			int num;
			if(colList.size()>4) {//只自动生成三个搜索输入框
				num=4;
			}else {
				num = colList.size();
			}
			for(int i=0;i<num;i++){
				Column column = colList.get(i);
				if(column.isPkAuto())
					continue;//主键id自动生成，不进行操作
				String colName = column.getEntityField();
				String colZHName = column.getRemark();//列中文名，待完善 TODO
				search_input += n+
						"						<div class=\"col-xs-3\">\r\n" + 
						"								<input type=\"text\" class=\"form-control \" id=\""+colName+"\" name=\""+colName+"\"\r\n" + 
						"									th:value=\"${searchMap['"+colName+"']}\" placeholder=\"请输入"+colZHName+"\">\r\n" + 
						"						</div>";
			}
			//搜索按钮
			search_input +=n+
					"						<div class=\"col-xs-2\">\r\n" + 
					"							<input type=\"button\" class=\"search-btn btn btn-primary\"\r\n" + 
					"								onclick=\"search()\" style=\"width: 100%;\" value=\"搜索\">\r\n" + 
					"						</div>" ;
		}
		//searchBar拼接
		str +=n+search_input+n+
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"			</form>\r\n" + 
				"		</div>";
		return str;
	}

	//body之后的script
	private String behindScript() {
		String str = n+
				"<script th:inline=\"javascript\">\r\n" + 
				"    var message = [[${message}]];\r\n" + 
				"    \r\n" + 
				"    if(message==\"delectsuccess\"){\r\n" + 
				"    	message=\"删除成功！\"\r\n" + 
				"    }\r\n" + 
				"    if(message==\"updatesuccess\"){\r\n" + 
				"    	message=\"修改成功！\"\r\n" + 
				"    }\r\n" + 
				"    if(message==\"savesuccess\"){\r\n" + 
				"    	message=\"保存成功！\"\r\n" + 
				"    }\r\n" + 
				"    if(message==\"delectfailure\"){\r\n" + 
				"    	message=\"删除失败！\"\r\n" + 
				"    }\r\n" + 
				"    if(message==\"updatefailure\"){\r\n" + 
				"    	message=\"修改失败！\"\r\n" + 
				"    }\r\n" + 
				"    if(message==\"savefailure\"){\r\n" + 
				"    	message=\"保存失败！\"\r\n" + 
				"    }\r\n" + 
				"    if(message != null && message != \"\"){							    	\r\n" + 
				"   		alert(message);							   		 \r\n" + 
				"   	}							   \r\n" + 
				"</script>";
		return str;
	}
	//body之前的script
	private String aheadScript() {
		String str = n+
				"<script type=\"text/javascript\">\r\n" + 
				"	var url =\"/"+entityName+"/list?\";\r\n" + 
				"	var pageUrl =url+\"pageNum=\";\r\n" + 
				"	var pageNum= [[${pageNum}]];   //当前页数\r\n" + 
				"	var totalPage = [[${TotalPages}]]; //总共页数\r\n" + 
				"	\r\n" + 
				"	//将url中拼接的空条件去除\r\n" + 
				"	function serializeNotNull(serStr){\r\n" + 
				"	    return serStr.split(\"&\").filter(str => !str.endsWith(\"=\")).join(\"&\");\r\n" + 
				"	}\r\n" + 
				"	//根据表单form获得查询url的条件\r\n" + 
				"	function getFormParams(serId){\r\n" + 
				"		return serializeNotNull($(\"#search_form\").serialize());\r\n" + 
				"	}\r\n" + 
				"	//获取查询url\r\n" + 
				"	function searchUrl(pageNum){\r\n" + 
				"		return pageUrl+pageNum+\"&\"+getFormParams(\"#search_form\");\r\n" + 
				"	}\r\n" + 
				"	//搜索\r\n" + 
				"	function search(){\r\n" + 
				"		var form_data = getFormParams(\"#search_form\");\r\n" + 
				"		console.log(url+form_data);\r\n" + 
				"		window.location.href=url+form_data;\r\n" + 
				"	}\r\n" + 
				"	//第一页\r\n" + 
				"	function firstPage(){\r\n" + 
				"	    if(1==pageNum){\r\n" + 
				"	        alert(\"已经是首页了\");\r\n" + 
				"	    }else{\r\n" + 
				"	    	pageNum = 1 ;\r\n" + 
				"			window.location.href=searchUrl(pageNum);\r\n" + 
				"	    }\r\n" + 
				"	}\r\n" + 
				"	//上一页\r\n" + 
				"	function prePage(){\r\n" + 
				"	    if(1==pageNum){\r\n" + 
				"	        alert(\"已经是首页了\");\r\n" + 
				"	    }else{\r\n" + 
				"		    pageNum--;\r\n" + 
				"		    window.location.href=searchUrl(pageNum);\r\n" + 
				"	    }\r\n" + 
				"	}\r\n" + 
				"	//下一页\r\n" + 
				"	function nextPage(){\r\n" + 
				"	    if(pageNum==totalPage){\r\n" + 
				"	        alert(\"已经是尾页了\");\r\n" + 
				"	    }else{\r\n" + 
				"		    pageNum++;\r\n" + 
				"		    window.location.href=searchUrl(pageNum);\r\n" + 
				"	    }\r\n" + 
				"	}\r\n" + 
				"	//尾页\r\n" + 
				"	function lastPage(){\r\n" + 
				"	    if(pageNum==totalPage){\r\n" + 
				"	        alert(\"已经是尾页了\");\r\n" + 
				"	    }else{\r\n" + 
				"	    	 pageNum=totalPage;\r\n" + 
				"	    	 window.location.href=searchUrl(pageNum);\r\n" + 
				"	    }\r\n" + 
				"	 }\r\n" + 
				"	//刷新\r\n" + 
				"	function myrefresh(){\r\n" + 
				"	  window.location.reload();\r\n" + 
				"	}\r\n" + 
				"	//删除\r\n" + 
				"	function deleteitems(id){\r\n" + 
				"		var msg = \"您真的确定要删除吗？\\n\\n请确认！\";\r\n" + 
				"		if (confirm(msg)==true){\r\n" + 
				"			window.location.href=\"/"+entityName+"/delect/\"+id;\r\n" + 
				"		}else{\r\n" + 
				"			return false;\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"</script>";
		return str;
	}
	//HTML<head>
	private String htmlHead() {
		String str = n+ 
				"<head>\r\n" + 
				"<meta charset=\"utf-8\" />\r\n" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n" + 
				"<meta name=\"list\" content=\"width=device-width, initial-scale=1\" />\r\n" + 
				"<script th:src=\"@{/Content/assets/lib/jquery-2.1.1.min.js}\"></script>\r\n" + 
				"<script th:src=\"@{/Content/assets/lib/aYin/aYin.js}\"></script>\r\n" + 
				"<script th:src=\"@{/Content/assets/lib/bootstrap/js/bootstrap.min.js}\"></script>\r\n" + 
				"<script th:src=\"@{/Content/assets/init/loadFiles.js}\"></script>\r\n" + 
				"\r\n" + 
				"<script th:src=\"@{/Content/js/datepicker/WdatePicker.js}\"></script>\r\n" + 
				"<link th:href=\"@{/Content/js/datepicker/skin/WdatePicker.css}\" rel=\"stylesheet\" />\r\n" + 
				"</head>";
		return str;
	}

}
