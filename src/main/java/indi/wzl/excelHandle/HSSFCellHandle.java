package indi.wzl.excelHandle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import indi.wzl.annotation.ExcelCell;
import indi.wzl.annotation.HeadName;
import indi.wzl.util.ClassUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.interfac.CellHandle;
import indi.wzl.util.HSSFCellUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 
* @ClassName: HSSFCellHandle 
* @Description: TODO(HSSF单元格处理类) 
* @author wuzonglin
* @date 2015年11月17日 下午2:59:58 
*
 */
public class HSSFCellHandle implements CellHandle {

	@Override
	public Object parseCell(Object obj, Field field)
			throws SimpleExcelException {
		HSSFCell hssfCell = (HSSFCell)obj;
		return HSSFCellUtil.getValue(hssfCell,field);
	}

	@Override
	public <S> void reflectHeadCell(Cell cell, Field field,Class<S> sClass) throws SimpleExcelException {
		HeadName headName = field.getAnnotation(HeadName.class);
		String headNameStr = "标题";
		if(null != headName){
			headNameStr = headName.value();
		}
		HSSFRichTextString text = new HSSFRichTextString(headNameStr);
		cell.setCellValue(text);
	}

	@Override
	public <S> void reflectCell(Cell cell, Field field, S s) throws SimpleExcelException {
		try {
			Object value = ClassUtil.getProperty(s,field.getName());
			value = TypeCastHandle.typeCast(field,value);
			HSSFRichTextString richString = new HSSFRichTextString(value.toString());
			cell.setCellValue(richString);
		} catch (Exception e) {
			throw new SimpleExcelException("",e);
		}

	}

}
