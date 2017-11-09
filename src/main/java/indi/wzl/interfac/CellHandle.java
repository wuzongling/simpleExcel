package indi.wzl.interfac;

import java.lang.reflect.Field;

import indi.wzl.Exception.SimpleExcelException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 
* @ClassName: CellHandle 
* @Description: TODO(单元格处理接口) 
* @author wuzonglin
* @date 2015年11月17日 上午9:58:49 
*
 */
public interface CellHandle {
	/**
	 * 解析单元格
	 * @param obj
	 * @param field
	 * @return
	 * @throws SimpleExcelException
	 */
	public Object parseCell(Object obj,Field field) throws SimpleExcelException;

	/**
	 * 反射生成表头单元格
	 * @param cell
	 * @param field
	 * @throws SimpleExcelException
	 */
	public <S> void reflectHeadCell(Cell cell, Field field, Class<S> sClass)throws SimpleExcelException;

	/**
	 * 反射生成单元格
	 * @param cell
	 * @param field
	 * @throws SimpleExcelException
	 */
	public <S> void reflectCell(Cell cell,Field field,S s)throws SimpleExcelException;
}
