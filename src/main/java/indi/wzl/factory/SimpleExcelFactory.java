package indi.wzl.factory;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.bean.ExportCell;
import indi.wzl.excelHandle.HSSFExcelHandle;
import indi.wzl.excelHandle.XSSFExcelHandle;
import indi.wzl.excelHandle.XSSFRowHandle;
import indi.wzl.interfac.ExcelHandle;
import indi.wzl.util.HttpUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class SimpleExcelFactory {
	private static XSSFExcelHandle xssfExcelHandle = null;
	private static HSSFExcelHandle hssfExcelHandle = null;
	
	public static XSSFExcelHandle getXSSFExcelHandle(){
		if(xssfExcelHandle == null){
			xssfExcelHandle = new XSSFExcelHandle();
		}
		return xssfExcelHandle;
	}
	
	public static HSSFExcelHandle getHSSFExcelHandle(){
		if(hssfExcelHandle == null){
			hssfExcelHandle = new HSSFExcelHandle();
		}
		return hssfExcelHandle;
	}
	
	/**
	 * 读取excel
	 * @param httpUrl http地址的url
	 * @param cla
	 * @return
	 * @throws IOException
	 * @throws SimpleExcelException
	 * @desc 1. 如果excel表格有多个sheet页,则返回map集合,key值为sheet1、sheet2等。 2.如果excel表格只有一个sheet则返回list<T>集合
	 */
	public static <T> Object readExcelforHttp(String httpUrl,Class<T> cla) throws IOException, SimpleExcelException{
		String suffix = httpUrl.substring(httpUrl.lastIndexOf(".") + 1);
		InputStream is = HttpUtil.download(httpUrl);
		
		if("xls".equals(suffix)){
			//2003 - 2007
			return readXls(is, cla);
		}else if("xlsx".equals(suffix)){
			//2010
			return readXlsx(is,cla);
		}
		return null;
	}
	
	/**
	 * 读取excel
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws SimpleExcelException 
	 * @desc 1. 如果excel表格有多个sheet页,则返回map集合,key值为sheet1、sheet2等。 2.如果excel表格只有一个sheet则返回list<T>集合
	 */
	public static <T> Object readExcel(String filePath,Class<T> cla) throws IOException, SimpleExcelException{
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
		InputStream is = new FileInputStream(filePath);
		
		if("xls".equals(suffix)){
			//2003 - 2007
			return readXls(is, cla);
		}else if("xlsx".equals(suffix)){
			//2010
			return readXlsx(is,cla);
		}
		return null;
	}
	
	/**
	 * 读取xlsx
	 * @param is
	 * @param cla
	 * @return
	 * @throws SimpleExcelException
	 * @desc 1. 如果excel表格有多个sheet页,则返回map集合,key值为sheet1、sheet2等。 2.如果excel表格只有一个sheet则返回list<T>集合
	 */
	public static <T> Object readXlsx(InputStream is,Class<T> cla) throws SimpleExcelException{
		XSSFExcelHandle xrh = getXSSFExcelHandle();
		return xrh.parseExcel(is, cla);
	}
	
	/**
	 * 读取xls
	 * @param is
	 * @param cla
	 * @return
	 * @throws SimpleExcelException
	 * @desc 1. 如果excel表格有多个sheet页,则返回map集合,key值为sheet1、sheet2等。 2.如果excel表格只有一个sheet则返回list<T>集合
	 */
	public static <T> Object readXls(InputStream is,Class<T> cla) throws SimpleExcelException{
		HSSFExcelHandle hch = getHSSFExcelHandle();
		return hch.parseExcel(is, cla);
	}

	/**
	 * 将java bean的数据导出成excel
	 * 如果传入includeName数组，则@ExcelCell标签不起作用，cell单元格的顺序和includeName数组顺序一致
	 * @param t
	 * @param sClass
	 * @param out
	 * @param includeName 自定义的sClass类属性名数组
	 * @param <S>
	 * @param <T>
	 * @throws SimpleExcelException
	 */
	public static <S,T extends List> void export(T t, Class<S> sClass, OutputStream out,String ...includeName)throws SimpleExcelException{
		ExcelHandle excelHandle = getXSSFExcelHandle();
		if(null != includeName && includeName.length > 0){
			ExportCell[] exportCells = ExportCellFactory.getExportCells(includeName,sClass);
			excelHandle.exportExcel(t,sClass,out,exportCells);
		}else {
			excelHandle.exportExcel(t,sClass,out);
		}
	}

	/**
	 * 将java bean的数据导出成excel
	 * 如果传入includeName数组，则@ExcelCell标签不起作用，cell单元格的顺序和includeName数组顺序一致
	 * @param t
	 * @param sClass
	 * @param out
	 * @param includeName 自定义的sClass类属性名数组
	 * @param headNames 自定义的表头名数组，和includeName一一对应
	 * @param <S>
	 * @param <T>
	 * @throws SimpleExcelException
	 */
	public static <S,T extends List> void export(T t, Class<S> sClass, OutputStream out,String [] includeName,String [] headNames)throws SimpleExcelException{
		ExcelHandle excelHandle = getXSSFExcelHandle();
		ExportCell[] exportCells = ExportCellFactory.getExportCells(includeName,headNames);
		excelHandle.exportExcel(t,sClass,out,exportCells);
	}

}
