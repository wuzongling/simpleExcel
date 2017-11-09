package indi.wzl.excelHandle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import indi.wzl.bean.ExportCell;
import indi.wzl.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.annotation.ExcelCell;
import indi.wzl.interfac.RowHandle;
import indi.wzl.util.ClassUtil;

public class XSSFRowHandle implements RowHandle {
	private XSSFCellHandle xssfCellHandle = null;
	
	public XSSFCellHandle getXSSFCell(){
		if(xssfCellHandle == null){
			xssfCellHandle = new XSSFCellHandle();
		}
		return xssfCellHandle;
	}
	
	@Override
	public <T> T parseRow(Object obj, Class<T> cla) throws SimpleExcelException {
		XSSFRow xssfRow = (XSSFRow) obj;
		if(!checkRow(xssfRow)){
			return null;
		}
		Field[] fields = cla.getDeclaredFields();
		T t = null;
		try {
			t = cla.newInstance();
			for (Field field : fields) {
				//字段名
				String fileName = field.getName();
				//ExcelCell注解的值
				Integer val = (Integer)ClassUtil.getAnnotationMethodValue(cla,fileName , ExcelCell.class, "value");
				if(val != null){
					XSSFCellHandle xch = getXSSFCell();
					if(xssfRow.getLastCellNum()>=val){
						XSSFCell cell = xssfRow.getCell(val);
						Object arg = null;
						if(cell != null){
							//解析单元格
							arg = xch.parseCell(cell,field);
						}
						arg = TypeCastHandle.typeCast(field, arg);
						ClassUtil.setProperty(t, fileName, arg);

					}
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception
			throw new SimpleExcelException("解析表格行失败",e);
		}
		return t;
	}

	@Override
	public <S, T extends List> void reflectRow(Row row, S s, Class<S> sClass) throws SimpleExcelException {
		Field[] fields = sClass.getDeclaredFields();
		XSSFCellHandle xch = getXSSFCell();
		int rowNum = row.getRowNum();
		for (int i = 0; i<fields.length; i++){
			ExcelCell excelCell = fields[i].getAnnotation(ExcelCell.class);
			int cellInt = 0;
			if(null != excelCell){
				cellInt = excelCell.value();
				if(rowNum > 0){
					//解析单元格
					xch.reflectCell(row.createCell(cellInt),fields[i],s);
				}else {
					//解析表头
					xch.reflectHeadCell(row.createCell(cellInt),fields[i],sClass);
				}
			}
		}
	}

	public <S, T extends List> void reflectRow(Row row, S s, Class<S> sClass, ExportCell[] exportCells) throws SimpleExcelException, NoSuchFieldException {
		XSSFCellHandle xch = getXSSFCell();
		int rowNum = row.getRowNum();
		for (int i = 0; i < exportCells.length; i++){
			ExportCell exportCell = exportCells[i];
			String fileName = exportCell.getFileName();
			String headName = exportCell.getHeadName();
			if(rowNum > 0){
				Field field = sClass.getDeclaredField(fileName);
				//设置单元格
				xch.reflectCell(row.createCell(i),field,s);
			}else {
				//设置表头
				xch.setCellValue(row.createCell(i),headName);
			}
		}
	}

	@Override
	public <S> void reflectHead(Row head, Class<S> sClass) throws SimpleExcelException {
		/*Field[] fields = sClass.getDeclaredFields();
		XSSFCellHandle xch = getXSSFCell();
		for (int i = 0; i<fields.length; i++){
			ExcelCell excelCell = fields[i].getAnnotation(ExcelCell.class);
			int cellInt = 0;
			if(null != excelCell){
				cellInt = excelCell.value();
				xch.reflectHeadCell(head.createCell(cellInt),fields[i],sClass);
			}
		}*/
	}

	/**
	 * 校验行
	 * @param row
	 * @return
	 */
	public boolean checkRow(Row row){
		boolean flag = false;
		for(int i = 0; i < row.getLastCellNum(); i++){
			Cell cell = row.getCell(i);
			if(null != cell){
				if(checkCellStrNull(cell)){
					return true;
				}
			}
		}
		return flag;
	}

	/**
	 * 校验字符串格式单元格
	 * @param cell
	 * @return
	 */
	protected boolean checkCellStrNull(Cell cell){
		boolean flag = true;
		int cellType = cell.getCellType();
		if(Cell.CELL_TYPE_STRING == cellType || Cell.CELL_TYPE_BLANK == cellType){
			String value = cell.getStringCellValue();
			if(StringUtil.isNull(value)){
				return false;
			}
		}
		return flag;
	}

}
