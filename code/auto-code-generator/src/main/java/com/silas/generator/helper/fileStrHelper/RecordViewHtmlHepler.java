package com.silas.generator.helper.fileStrHelper;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class RecordViewHtmlHepler implements CreateFileHelper,HtmlHelper{
	String HtmlZHName="修改";
	String htmlName ="updateView.html";

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

	// body之后的script
	private String htmlBody() {
		String str = n + 
				"<body>\r\n" + 
				"	<div class=\"top-bar\">\r\n" + 
				"		<ul class=\"breadcrumb top-breadcrumb\">\r\n" + 
				"			<li><i class=\"fa fa-home\"></i></li>\r\n" + 
				"			<li>"+moduleName+"</li>\r\n" + 
				"			<li><a href=\"/"+entityName+"/list\">"+moduleName+"列表</a></li>\r\n" + 
				"			<li>"+moduleName+HtmlZHName+"</li>\r\n" + 
				"		</ul>\r\n" + 
				"		<ul class=\"top-toolbar\"></ul>\r\n" + 
				"	</div>\r\n" + 
				"	<div align=\"center\">\r\n" + 
				"		<form action=\"#\" th:action=\"@{/"+entityName+"/save}\" th:object=\"${"+entityLowerName+"}\"\r\n" + 
				"			method=\"post\">\r\n"+
				"			<p>\r\n" + 
				"				<input type=\"text\" th:field=\"*{"+primary_col.getEntityField()+"}\" hidden=\"false\" />\r\n" + 
				"			</p>" + 
				"			<div class=\"panel panel-line\">\r\n" + 
				"				<div class=\"panel-heading\">\r\n" + 
				"					<span class=\"panel-title\">"+HtmlZHName+" "+moduleName+" 记录</span>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"panel-body\">" ;
		String bodyTable= bodyTable();
		str +=bodyTable+n+
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"		</form>\r\n" + 
				"	</div>\r\n" + 
				"</body>";
		return str;
	}
	
	// body之后的script
	private String bodyTable() {
		String str =n+
				"					<table class=\"table-form\">\r\n";

		if(colList==null||colList.size()==0) {
			System.out.println("未获取 到表的列元数据！");
			return "";
		}
		String tableTrs = "";	
		int i=0;
		//获得所有th和td
		for(Column column :colList) {
			String columnName = column.getEntityField();
			String columnZHName = column.getRemark();//中文字段名 TODO
			if(column.isPk()) {//自动生成主键，则无需前端输入
				continue;
			}
			String thTd = "";
			if(column.getColumnTypeHelper().getClassName().equals("Date")) {
				thTd =
						tab3+"                  <th width=\"150\">"+columnZHName+"</th>\r\n" + 
						tab3+"                  <td><input id=\""+columnName+"\" name=\""+columnName+"\"  th:field=\"*{"+columnName+"}\" type=\"text\" class=\"form-control input-wdatepicker\" onfocus=\"WdatePicker()\" placeholder=\"请选择"+columnZHName+"\"/></td>\r\n";
			}else {
				thTd = 
						tab3+"                  <th width=\"150\">"+columnZHName+"</th>\r\n" + 
						tab3+"                  <td><input  id=\""+columnName+"\" name=\""+columnName+"\" type=\"text\" class=\"form-control\" th:field=\"*{"+columnName+"}\" placeholder=\"请输入"+columnZHName+"\"/></td>\r\n"; 
				
			}
			if(i%2==0) {
				tableTrs+="						<tr>"+n + thTd;
			}else {
				tableTrs+=thTd+"						</tr>\r\n";
			}
			i++;
		}
		if(i%2==1) {//
			tableTrs+="						</tr>\r\n";
		}
		str += tableTrs+
				"						<tr align=\"center\">\r\n" + 
				"							<td colspan=\"4\" align=\"center\">\r\n" + 
				"								<div class=\"col-xs-4\">\r\n" + 
				"									<div class=\" btn-group btn-group-justified opinion-button\"\r\n" + 
				"										role=\"group\" aria-label=\"...\">\r\n" + 
				"										<div class=\"btn-group active\" role=\"group\">\r\n" + 
				"											<button type=\"submit\" class=\"btn btn-default\">提交</button>\r\n" + 
				"										</div>\r\n" + 
				"										<div class=\"btn-group\" role=\"group\">\r\n" + 
				"											<button type=\"reset\" class=\"btn btn-default\">重置</button>\r\n" + 
				"										</div>\r\n" + 
				"									</div>\r\n" + 
				"								</div>\r\n" + 
				"							</td>\r\n" + 
				"						</tr>\r\n" + 
				"					</table>";
		return str;
	}

	// body之后的script
	private String behindScript() {
		String str ="";
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
				"<meta charset=\"utf-8\" />\r\n" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n" + 
				"<meta name=\"add\" content=\"width=device-width, initial-scale=1\" />\r\n" + 
				"<script th:src=\"@{/Content/assets/lib/jquery-2.1.1.min.js}\"></script>\r\n" + 
				"<script th:src=\"@{/Content/assets/lib/aYin/aYin.js}\"></script>\r\n" + 
				"<script th:src=\"@{/Content/assets/lib/bootstrap/js/bootstrap.min.js}\"></script>\r\n" + 
				"<script th:src=\"@{/Content/js/datepicker/WdatePicker.js}\"></script>"+n+
				"<link th:href=\"@{/Content/assets/css/main.css}\" rel=\"stylesheet\" />\r\n" + 
				"<link th:href=\"@{/Content/assets/lib/fontawesome-pro/css/fontawesome.css}\" rel=\"stylesheet\" />\r\n" + 
				"<link th:href=\"@{/Content/assets/lib/bootstrap/css/bootstrap.min.css}\" rel=\"stylesheet\" />\r\n" + 
				"<link th:href=\"@{/Content/assets/lib/aYin/aYin.css}\" rel=\"stylesheet\" />\r\n"+
				"<link th:href=\"@{/Content/js/bootstrap/css/bootstrap-datetimepicker.min.css}\" rel=\"stylesheet\" />" +n+
				"</head>";
		return str;
	}

}
