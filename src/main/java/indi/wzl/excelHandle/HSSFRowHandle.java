package indi.wzl.excelHandle;

import java.lang.reflect.Field;
import java.util.List;

import indi.wzl.annotation.HeadName;
import indi.wzl.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.annotation.ExcelCell;
import indi.wzl.interfac.RowHandle;
import indi.wzl.util.ClassUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * 
* @ClassName: HSSFRowHandle 
* @Description: TODO(HSSF行处理类) 
* @author wuzonglin
* @date 2015年11月17日 下午3:10:38 
*
 */
public class HSSFRowHandle implements RowHandle {
	private HSSFCellHandle hssfCellHandle = null;
	
	public HSSFCellHandle getHSSFCellHandle(){
		if(hssfCellHandle == null){
			hssfCellHandle = new HSSFCellHandle();
		}
		return hssfCellHandle;
	}
	
	@Override
	public <T> T parseRow(Object obj, Class<T> cla) throws SimpleExcelException {
		HSSFRow hssfRow = (HSSFRow) obj;
		if(!checkRow(hssfRow)){
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
					HSSFCellHandle hch = getHSSFCellHandle();
					if(hssfRow.getLastCellNum()>=val){
						HSSFCell cell = hssfRow.getCell(val);
						Object arg = null;
						if(cell != null){
							//解析单元格
							arg = hch.parseCell(cell,field);
						}
						//解析单元格
						arg = TypeCastHandle.typeCast(field, arg);
						ClassUtil.setProperty(t, fileName,arg);
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
	public <S, T extends List> void reflectRow(Row row,S s,Class<S> sClass) throws SimpleExcelException {
		Field[] fields = sClass.getDeclaredFields();
		HSSFCellHandle hch = getHSSFCellHandle();
		for (int i = 0; i<fields.length; i++){
			ExcelCell excelCell = fields[i].getAnnotation(ExcelCell.class);
			int cellInt = 0;
			if(null != excelCell){
				cellInt = excelCell.value();
				hch.reflectCell(row.createCell(cellInt),fields[i],s);
			}
		}
	}

	@Override
	public <S> void reflectHead(Row head, Class<S> sClass) throws SimpleExcelException {
		Field[] fields = sClass.getDeclaredFields();
		HSSFCellHandle hch = getHSSFCellHandle();
		for (int i = 0; i<fields.length; i++){
			ExcelCell excelCell = fields[i].getAnnotation(ExcelCell.class);
			int cellInt = 0;
			if(null != excelCell){
				cellInt = excelCell.value();
				hch.reflectHeadCell(head.createCell(cellInt),fields[i],sClass);
			}
		}
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
