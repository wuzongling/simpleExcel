package indi.wzl.interfac;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.bean.ExportCell;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 
* @ClassName: ExcelHandle 
* @Description: TODO(excel表格处理接口) 
* @author wuzonglin
* @date 2015年11月17日 上午9:55:55 
*
 */
public interface ExcelHandle {
	/**
	 * 解析excel表格
	 * @param is
	 * @param cla
	 * @return
	 * @throws SimpleExcelException
	 */
	public <T> Object parseExcel(InputStream is,Class<T> cla) throws SimpleExcelException;

	/**
	 * 导出excel表格
	 * @param t
	 * @param sClass
	 * @param out
	 * @param <S>
	 * @param <T>
	 * @throws SimpleExcelException
	 */
	public <S,T extends List<S>> void exportExcel(T t, Class<S> sClass, OutputStream out)throws SimpleExcelException;

	/**
	 * 根据exportCells动态的导出
	 * @param t
	 * @param sClass
	 * @param out
	 * @param exportCells
	 * @param <S>
	 * @param <T>
	 * @throws SimpleExcelException
	 */
	public <S,T extends List<S>> void exportExcel(T t, Class<S> sClass, OutputStream out, ExportCell[] exportCells)throws SimpleExcelException;
}
