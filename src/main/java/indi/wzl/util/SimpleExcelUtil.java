package indi.wzl.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExcelUtil {
	
	/**
	 * 读取excel
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @desc 每个sheet页用map存储key为sheet1、sheet2等；每行数据用list存储，放在sheet的map中；每个单元格cell用list存储,放在对应的row所在的list的中
	 */
	public static Map<String,List<List<Object>>> readExcel(String filePath) throws IOException{
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
		InputStream is = new FileInputStream(filePath);
		if("xls".equals(suffix)){
			//2003 - 2007
			return readXls(is);
		}else if("xlsx".equals(suffix)){
			//2010
			return readXlsx(is);
		}
		return null;
	}
	
	
	/** 
	 * 读取excel 2003 - 2007
	 * @param is
	 * @return
	 * @throws IOException
	 * @desc 每个sheet页用map存储key为sheet1、sheet2等；每行数据用list存储，放在sheet的map中；每个单元格cell用list存储,放在对应的row所在的list的中
	 */
	public static Map<String,List<List<Object>>> readXls(InputStream is) throws IOException{
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//sheet map
		 Map<String,List<List<Object>>> sheetMap = new HashMap<String, List<List<Object>>>();
		 for (int sheetNum = 0; sheetNum < hssfWorkbook.getNumberOfSheets(); sheetNum++) {
			 	//读取sheet
			 	HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
				//row list
				List<List<Object>> rowList = new ArrayList<List<Object>>();
				if (hssfSheet == null) {
					continue;
				}
				for(int rowNum = 1;rowNum<hssfSheet.getLastRowNum();rowNum++){
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					//cell list
					List<Object> cellList = new ArrayList<Object>();
					for(int cellNum = 0;cellNum<hssfRow.getLastCellNum();cellNum++){
						HSSFCell hssfCell = hssfRow.getCell(cellNum);
						Object obj = getValue(hssfCell);
						cellList.add(obj);
					}
					if(cellList.size()>0){
						rowList.add(cellList);
					}
				}
				if(rowList.size()>0){
					sheetMap.put("sheet"+(sheetNum+1), rowList);
				}
		 }
			
		return sheetMap;
	}
	
	/**
	 * 读取xlsx格式的excel  2010
	 * @param is
	 * @return
	 * @throws IOException
	 * @desc 每个sheet页用map存储key为sheet1、sheet2等；每行数据用list存储，放在sheet的map中；每个单元格cell用list存储,放在对应的row所在的list的中
	 */
	public static Map<String,List<List<Object>>> readXlsx(InputStream is) throws IOException{
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		//sheet map
		Map<String,List<List<Object>>> sheetMap = new HashMap<String, List<List<Object>>>();
		for (int sheetNum = 0; sheetNum < xssfWorkbook.getNumberOfSheets(); sheetNum++) {
			//读取sheet
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNum);
			//row list
			List<List<Object>> rowList = new ArrayList<List<Object>>();
			
			if (xssfSheet == null) {
				continue;
			}
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				//读取行
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				//cell list
				List<Object> cellList = new ArrayList<Object>();
				for(int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++){
					XSSFCell xssfCell = xssfRow.getCell(cellNum);
					Object obj = getValue(xssfCell);
					//System.out.println(obj);
					cellList.add(obj);
				}
				if(cellList.size()>0){
					rowList.add(cellList);
				}
			}
			if(rowList.size()>0){
				sheetMap.put("sheet"+(sheetNum+1), rowList);
			}
		}
		//System.out.println(sheetMap);
		return sheetMap;
	}
	
	/**
	 * 获取单元格值
	 * @param xssfCell
	 * @return
	 */
	private static Object getValue(XSSFCell xssfCell){
		int cellType = xssfCell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_NUMERIC:
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
	 * @param hssfCell
	 * @return
	 */
	private static Object getValue(HSSFCell hssfCell){
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
	 * @param xssfCell
	 * @param formula
	 * @return
	 */
	private static Object getValue(XSSFCell xssfCell,String formula){
		switch (getFormulaType(formula)) {
		case 1:
			return xssfCell.getDateCellValue();

		default:
			return xssfCell.getRawValue();
		}
	}
	
	/**
	 * 获取单元格值
	 * @param hssfCell
	 * @param formula
	 * @return
	 */
	private static Object getValue(HSSFCell hssfCell,String formula){
		switch (getFormulaType(formula)) {
		case 1:
			return hssfCell.getDateCellValue();

		default:
			return hssfCell.getStringCellValue();
		}
	}
	
	/**
	 * 获取单元格formula类型  目前只支持DATE类型
	 * @param formula
	 * @return
	 */
	private static int getFormulaType(String formula){
		int date = formula.indexOf("DATE");
		if(date != -1){
			return 1;
		}
		return 0;
	}
	
	/*public static void main(String[] args) {
		try {
			readExcel("C:\\Users\\Administrator\\Desktop\\excelTest.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
