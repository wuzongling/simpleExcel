package indi.wzl.excelHandle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.wzl.bean.ExportCell;
import indi.wzl.factory.ExportCellFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.interfac.ExcelHandle;

public class XSSFExcelHandle implements ExcelHandle {
	private XSSFRowHandle xssfRowHandle = null;
	
	/**
	 * 获取XSSFRowHandle单例
	 * @return
	 */
	public XSSFRowHandle getXSSFRowHandle(){
		if(xssfRowHandle == null){
			xssfRowHandle = new XSSFRowHandle();
		}
		return xssfRowHandle;
	}
	
	@Override
	public <T> Object parseExcel(InputStream is, Class<T> cla) throws SimpleExcelException {
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			Map<String,Object> sheetMap = new HashMap<String,Object>();
			for (int sheetNum = 0; sheetNum < xssfWorkbook.getNumberOfSheets(); sheetNum++) {
				//读取sheet
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNum);
				XSSFRowHandle xssfRowHandle = getXSSFRowHandle();
				List<T> rowList = new ArrayList<T>();
				//没有数据不解析
				int i = xssfSheet.getLastRowNum();
				for(int rowNum = 1;rowNum <= xssfSheet.getLastRowNum();rowNum++){
					//解析行
					T t = xssfRowHandle.parseRow(xssfSheet.getRow(rowNum), cla);
					if(null != t){
						rowList.add(t);
					}
				}
				if(rowList.size()>0){
					sheetMap.put("sheet"+(sheetNum+1),rowList);
				}
			}
			if(sheetMap.entrySet().size() == 0){
				return null;
			}
			if(sheetMap.entrySet().size() == 1){
				List<T> list = (List)sheetMap.get("sheet1");
				return list;
			}
			return sheetMap;
		} catch (IOException e) {
			throw new SimpleExcelException("解析excel表格失败", e);
		}
	}

	@Override
	public <S, T extends List<S>> void exportExcel(T t, Class<S> sClass, OutputStream out) throws SimpleExcelException {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		workbook.createFont();
		XSSFSheet sheet = workbook.createSheet("数据导出");
		sheet.setDefaultColumnWidth((short) 15);
		XSSFRowHandle xssfRowHandle = getXSSFRowHandle();
		XSSFRow head = sheet.createRow(0);
		xssfRowHandle.reflectRow(head,null,sClass);
		for (int i =1;i<t.size()+1;i++){
			XSSFRow row = sheet.createRow(i);
			xssfRowHandle.reflectRow(row,t.get(i-1),sClass);
		}
		try {
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			throw new SimpleExcelException("excel表格输出失败",e);
		}
	}

	@Override
	public <S, T extends List<S>> void exportExcel(T t, Class<S> sClass, OutputStream out, ExportCell[] exportCells) throws SimpleExcelException {
		try {
			// 声明一个工作薄
			XSSFWorkbook workbook = new XSSFWorkbook();
			workbook.createFont();
			XSSFSheet sheet = workbook.createSheet("数据导出");
			sheet.setDefaultColumnWidth((short) 15);
			XSSFRowHandle xssfRowHandle = getXSSFRowHandle();
			XSSFRow head = sheet.createRow(0);
			//设置表头
			xssfRowHandle.reflectRow(head,null,sClass,exportCells);
			for (int i =1;i<t.size()+1;i++){
				XSSFRow row = sheet.createRow(i);
				//设置行数据
				xssfRowHandle.reflectRow(row,t.get(i-1),sClass,exportCells);
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw new SimpleExcelException("excel表格输出失败",e);
		}
	}

}
