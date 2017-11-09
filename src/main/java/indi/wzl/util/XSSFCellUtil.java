package indi.wzl.util;

import indi.wzl.constant.FormulaType;

import indi.wzl.constant.TypeConstant;
import indi.wzl.excelHandle.TypeCastHandle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.lang.reflect.Field;
import java.util.Date;

public class XSSFCellUtil extends CellUtil {
	/**
	 * 获取单元格值
	 * @param xssfCell
	 * @return
	 */
	public static Object getValue(XSSFCell xssfCell,Field field){
		int cellType = xssfCell.getCellType();
		int fieldType = TypeCastHandle.getType(field.getType());
		//Date date = xssfCell.getDateCellValue();
		switch (cellType) {
		case Cell.CELL_TYPE_NUMERIC:
			if(fieldType == TypeConstant.DATE){
				return xssfCell.getDateCellValue();
			}
			return xssfCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			return xssfCell.getStringCellValue();
		case Cell.CELL_TYPE_FORMULA:
			String formula = xssfCell.getCellFormula();
			return getValue(xssfCell,formula);
		case Cell.CELL_TYPE_BLANK:
			return xssfCell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return xssfCell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:	
			return xssfCell.getErrorCellString();
		default:
			return xssfCell.getRawValue();
		}
	}
	
	/**
	 * 获取单元格值
	 * @param xssfCell
	 * @param formula
	 * @return
	 */
	public static Object getValue(XSSFCell xssfCell,String formula){
		switch (getFormulaType(formula)) {
		case FormulaType.DATE:
			return xssfCell.getDateCellValue();

		default:
			return xssfCell.getRawValue();
		}
	}
}
