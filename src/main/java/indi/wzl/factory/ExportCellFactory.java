package indi.wzl.factory;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.bean.ExportCell;
import indi.wzl.util.CellUtil;

import java.lang.reflect.Field;

/**
 * 导出单元格工厂
 *
 * @author zonglin_wu
 * @create 2017-06-21 13:40
 **/
public class ExportCellFactory {

    /**
     * ExportCell工厂
     * @param fileNames
     * @param headNames
     * @return
     */
    public static ExportCell[]  getExportCells(String[] fileNames,String[] headNames){
        if(null != fileNames && fileNames.length !=0){
            ExportCell[] exportCells = new ExportCell[fileNames.length];
            String fileName;
            String headName;
            for (int i = 0; i< fileNames.length; i++){
                fileName = fileNames[i];
                headName = "";
                if(i < headNames.length){
                    headName = headNames[i];
                }
                ExportCell exportCell = new ExportCell(fileName,headName,i);
                exportCells[i] = exportCell;
            }
            return exportCells;
        }
        return null;
    }

    /**
     * ExportCell工厂
     * 根据aClass类里的字段field去取headName
     * @param fileNames
     * @param aClass
     * @return
     * @throws NoSuchFieldException
     */
    public static ExportCell[]  getExportCells(String[] fileNames,Class aClass) throws SimpleExcelException {
        try {
            if(null != fileNames && fileNames.length !=0){
                ExportCell[] exportCells = new ExportCell[fileNames.length];
                String fileName;
                for (int i = 0; i< fileNames.length; i++){
                    fileName = fileNames[i];
                    Field field = aClass.getDeclaredField(fileName);
                    String headName = CellUtil.getFieldHeadName(field);
                    ExportCell exportCell = new ExportCell(fileName,headName,i);
                    exportCells[i] = exportCell;
                }
                return exportCells;
            }
            return null;
        }catch (NoSuchFieldException e){
            throw new SimpleExcelException("获取ExportCell对象失败",e);
        }
    }
}
