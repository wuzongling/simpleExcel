package indi.wzl.excelHandle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.wzl.bean.ExportCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.interfac.ExcelHandle;

/**
 * 
* @ClassName: HSSFExcelHandle 
* @Description: TODO(HSSF表格处理类) 
* @author wuzonglin
* @date 2015年11月17日 下午3:10:19 
*
 */
public class HSSFExcelHandle implements ExcelHandle {
	private static HSSFRowHandle hssfRowHandle = null;
	public HSSFRowHandle getHSSFRowHandle(){
		if(hssfRowHandle == null){
			hssfRowHandle = new HSSFRowHandle();
		}
		return hssfRowHandle;
	}
	
	@Override
	public <T> Object parseExcel(InputStream is, Class<T> cla)
			throws SimpleExcelException {
		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			Map<String,Object> sheetMap = new HashMap<String,Object>();
			for (int sheetNum = 0; sheetNum < hssfWorkbook.getNumberOfSheets(); sheetNum++) {
				//读取sheet
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
				HSSFRowHandle hssfRowHandle = getHSSFRowHandle();
				List<T> rowList = new ArrayList<T>();
				for(int rowNum = 1;rowNum < hssfSheet.getLastRowNum();rowNum++){
					//解析行
					T t = hssfRowHandle.parseRow(hssfSheet.getRow(rowNum), cla);
					if(null != t){
						rowList.add(t);
					}
				}
				if(rowList.size()>0){
					sheetMap.put("sheet"+(sheetNum+1),rowList);
				}
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
	public <S,T extends List<S>> void exportExcel(T t, Class<S> sClass, OutputStream out)throws SimpleExcelException{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		workbook.createFont();
		HSSFSheet sheet = workbook.createSheet("数据导出");
		sheet.setDefaultColumnWidth((short) 15);
		HSSFRowHandle hssfRowHandle = getHSSFRowHandle();
		HSSFRow head = sheet.createRow(0);
		hssfRowHandle.reflectHead(head,sClass);
		for (int i =1;i<t.size();i++){
			HSSFRow row = sheet.createRow(i);
			hssfRowHandle.reflectRow(row,t.get(i),sClass);
		}
		try {
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			throw new SimpleExcelException("excel表格输出失败",e);
		}
	}

	@Override
	public <S, T extends List<S>> void exportExcel(T ss, Class<S> sClass, OutputStream out, ExportCell[] exportCells) throws SimpleExcelException {

	}

}
