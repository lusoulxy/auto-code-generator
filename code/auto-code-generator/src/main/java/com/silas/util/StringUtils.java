package com.silas.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 首字母转小写
	 * @param s
	 * @return
	 */
	public static String toLoweraseFirstOne(String s) {
		if(s==null)
			return null;
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if(s==null)
			return null;
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 下划线转驼峰法(默认小驼峰)
	 *
	 * @param line       源字符串
	 * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
	 * @return 转换后的字符串
	 */
	public static String underline2Camel(String line, boolean... smallCamel) {
		while (line.indexOf("_") > 0) {
			Integer indexOf = line.indexOf("_");
			line = line.replaceFirst("_", "");
			line = line.substring(0, indexOf)
					+ line.substring(indexOf, indexOf + 1).toUpperCase()
					+ line.substring(indexOf + 1, line.length());
		}
		if(smallCamel.length ==0 || smallCamel[0]) {//小驼峰
			line = toLoweraseFirstOne(line);
		}else {//大驼峰
			line = toUpperCaseFirstOne(line);
		}
		return line;
	}

	/**
	 * 驼峰法转下划线
	 *
	 * @param line 源字符串
	 * @return 转换后的字符串
	 */
	public static String camel2Underline(String line) {
		if (line == null || "".equals(line)) {
			return "";
		}
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toLowerCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}

	// 小驼峰命名法
	public static String getLowerCamelCase(String str) {
		return underline2Camel(str);
	}

	// 大驼峰命名法
	public static String getUperCamelCase(String str) {
		return underline2Camel(str, false);
	}

	//测试
	public static void main(String[] args) {
		String line = "CODE_MES_TEST";
		// 下划线转驼峰（大驼峰）
		// AreYouDouBiYellowcong
		String camel = underline2Camel(line, false);
		System.out.println(camel);

		// 下划线转驼峰（小驼峰）
		// areYouDouBiYellowcong
		camel = underline2Camel("TestAuto");
		System.out.println(camel);

		// 驼峰转下划线
		// ARE_YOU_DOU_BI_YELLOWCONG
		System.out.println(camel2Underline("TestAuto"));
		
		
		System.out.println("编号ID$not_show$".replaceAll("$", ""));
	}
}
