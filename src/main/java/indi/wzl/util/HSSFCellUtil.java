package indi.wzl.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Field;

/**
 * 
* @ClassName: HSSFCellUtil 
* @Description: TODO(HSSF单元格工具类) 
* @author wuzonglin
* @date 2015年11月17日 下午3:01:29 
*
 */
public class HSSFCellUtil extends CellUtil {
	/**
	 * 获取单元格值
	 * @param hssfCell
	 * @return
	 */
	public static Object getValue(HSSFCell hssfCell,Field field){
		int cellType = hssfCell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_NUMERIC:
			return hssfCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			return hssfCell.getStringCellValue();
		case Cell.CELL_TYPE_FORMULA:
			String formula = hssfCell.getCellFormula();
			return getValue(hssfCell,formula);
		case Cell.CELL_TYPE_BLANK:
			return hssfCell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return hssfCell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:	
			return hssfCell.getErrorCellValue();
		default:
			return hssfCell.getStringCellValue();
		}
	}
	
	/**
	 * 获取单元格值
	 * @param hssfCell
	 * @param formula
	 * @return
	 */
	public static Object getValue(HSSFCell hssfCell,String formula){
		switch (getFormulaType(formula)) {
		case 1:
			return hssfCell.getDateCellValue();

		default:
			return hssfCell.getStringCellValue();
		}
	}
}
