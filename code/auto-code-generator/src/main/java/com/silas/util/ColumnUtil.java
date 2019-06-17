package com.silas.util;

import java.util.List;

import com.silas.generator.helper.Column;

public class ColumnUtil {
	public static boolean hasColumn(List<Column> list ,String colName) {
		for(Column column : list) {
			if(column.getColumName().equals(colName))
				return true;
		}
		return false;
	}
}
