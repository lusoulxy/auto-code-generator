package com.silas.generator.helper;

import com.silas.generator.GeneratorUtil;
import com.silas.generator.OutPutFile;

public class RecordAddHtmlHepler implements CreateFileHelper{

	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + "/templates" +module+"/"+ entityName.toLowerCase() + "/add.html";
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}

}
