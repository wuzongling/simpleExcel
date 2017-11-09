package indi.wzl.excelHandle;

import java.lang.reflect.Field;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.annotation.HeadName;
import indi.wzl.util.CellUtil;
import indi.wzl.util.ClassUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import indi.wzl.interfac.CellHandle;
import indi.wzl.util.XSSFCellUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * 
* @ClassName: XSSFCellHandle 
* @Description: TODO(XSSF单元格处理类) 
* @author wuzonglin
* @date 2015年11月17日 上午10:55:30 
*
 */
public class XSSFCellHandle implements CellHandle {

	@Override
	public Object parseCell(Object obj,Field field) {
		XSSFCell xssfCell  = (XSSFCell) obj;
		return XSSFCellUtil.getValue(xssfCell,field);
	}

	@Override
	public <S> void reflectHeadCell(Cell cell, Field field, Class<S> sClass) throws SimpleExcelException {
		String headName = CellUtil.getFieldHeadName(field);
		XSSFRichTextString value = new XSSFRichTextString(headName);
		setCellValue(cell,value);
	}


	@Override
	public <S> void reflectCell(Cell cell, Field field, S s) throws SimpleExcelException {
		try {
			Object value = ClassUtil.getProperty(s,field.getName());
			value = TypeCastHandle.typeCast(field,value);
			setCellValue(cell,value);
		} catch (Exception e) {
			throw new SimpleExcelException("解析单元格失败:"+cell.toString(),e);
		}
	}

	/**
	 * 设置单元格值
	 * @param cell
	 * @param value
	 */
	public void setCellValue(Cell cell,Object value){
		XSSFRichTextString richString = new XSSFRichTextString(value.toString());
		cell.setCellValue(richString);
	}

}
