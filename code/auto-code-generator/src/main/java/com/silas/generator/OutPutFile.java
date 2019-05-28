package com.silas.generator;

import java.io.File;

/**
 * 用于输出
 * @author WuGuoDa
 * @version 时间：2019年5月24日 下午12:13:48
 */
public 	class OutPutFile {
	File file;
	String fileName;
	String fileOutputStr;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileOutputStr() {
		return fileOutputStr;
	}
	public void setFileOutputStr(String fileOutputStr) {
		this.fileOutputStr = fileOutputStr;
	}
}
