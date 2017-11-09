package indi.wzl.bean;

/**
 * 导出单元格
 *
 * @author zonglin_wu
 * @create 2017-06-21 13:31
 **/
public class ExportCell {
    //字段名字
    private String fileName;
    //表头
    private String headName;
    //序号
    private int index;

    public ExportCell(String fileName,String headName,int index){
        this.fileName = fileName;
        this.headName = headName;
        this.index = index;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
