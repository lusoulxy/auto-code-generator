package com.silas.generator.helper;

public interface HtmlHelper {
	// 文件头
	String htmlStart = "<!DOCTYPE html>\r\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\"\r\n"
			+ "	xmlns:th=\"http://www.thymeleaf.org\"\r\n"
			+ "	xmlns:sec=\"http://www.thymeleaf.org/thymeleaf-extras-springsecurity3\">";
	int colsLimit = 5;
}
