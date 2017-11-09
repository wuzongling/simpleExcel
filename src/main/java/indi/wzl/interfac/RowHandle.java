package indi.wzl.interfac;

import indi.wzl.Exception.SimpleExcelException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * 
* @ClassName: RowHandle 
* @Description: TODO(行处理接口) 
* @author wuzonglin
* @date 2015年11月17日 上午9:57:43 
*
 */
public interface RowHandle {
	/**
	 * 解析行
	 * @param obj
	 * @param cla
	 * @return
	 * @throws SimpleExcelException
	 */
	public <T> T parseRow(Object obj,Class<T> cla) throws SimpleExcelException;

	/**
	 * 反射生成行
	 * @param sheet
	 * @param t
	 * @param sClass
	 * @param <S>
	 * @param <T>
	 * @throws SimpleExcelException
	 */
	public <S,T extends List> void reflectRow(Row row, S s, Class<S> sClass)throws SimpleExcelException;

	/**
	 * 反射生成表头
	 * @param head
	 * @param sClass
	 * @param <S>
	 * @throws SimpleExcelException
	 */
	public <S> void reflectHead(Row head,Class<S> sClass)throws SimpleExcelException;
}
