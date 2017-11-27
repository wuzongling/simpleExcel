package indi.wzl.util;

import indi.wzl.annotation.HeadName;
import indi.wzl.constant.FormulaType;

import java.lang.reflect.Field;

public class CellUtil {
	
	public static String formulaDATE = "DATE";
	
	/**
	 * 获取单元格formula类型  目前只支持DATE类型
	 * @param formula
	 * @return
	 */
	public static int getFormulaType(String formula){
		int date = formula.indexOf(formulaDATE);
		if(date != -1){
			return FormulaType.DATE;
		}
		return FormulaType.NONE;
	}

	/**
	 * 获取字段的@headName属性值
	 * @param field
	 * @return
	 */
	public static String getFieldHeadName(Field field){
		HeadName headName = field.getAnnotation(HeadName.class);
		String headNameStr = field.getName();
		if(null != headName){
			headNameStr = headName.value();
		}
		return headNameStr;
	}
}
