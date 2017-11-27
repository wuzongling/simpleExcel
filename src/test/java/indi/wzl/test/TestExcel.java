package indi.wzl.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import indi.wzl.factory.SimpleExcelFactory;
import indi.wzl.test.bean.SignUp;
import org.junit.Test;

import javax.swing.*;
public class TestExcel{

	@Test
	public  void testexport1(){
		try
		{
			OutputStream out = new FileOutputStream("D://b.xls");
			List<SignUp> list = new ArrayList<>();
			for (int i = 0; i<4;i++){
				SignUp signUp = new SignUp();
				signUp.setCompany("ss");
				signUp.setMobile("ss");
				signUp.setName("123123");
				list.add(signUp);
			}
			SimpleExcelFactory.export(list,SignUp.class,out);
			out.close();
			JOptionPane.showMessageDialog(null, "导出成功!");
			System.out.println("excel导出成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void parse(){
		try {
			List list = (List)SimpleExcelFactory.readExcel("C:\\Users\\allen\\Desktop\\活动导入模板 (1).xlsx", SignUp.class);
			//InputStream is = HttpUtil.download("http://7u2hs1.com1.z0.glb.clouddn.com/4bd0007cbe7c5290541042bf0e60aa35-excelTest.xlsx");
			//List list = (List)SimpleExcelFactory.readXls(is, ExcelTest.class);
			System.out.println(JSON.toJSON(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void dynamicExport(){
		try
		{
			OutputStream out = new FileOutputStream("D://b.xls");
			List<SignUp> list = new ArrayList<>();
			for (int i = 0; i<4;i++){
				SignUp signUp = new SignUp();
				signUp.setCompany("ss");
				signUp.setMobile("17722448094");
				signUp.setName("123123");
				signUp.setMailbox("1369248650@qq.com");
				list.add(signUp);
			}
			String[] fileName  = {"name","mobile"};
			SimpleExcelFactory.export(list,SignUp.class,out,fileName);
			out.close();
			JOptionPane.showMessageDialog(null, "导出成功!");
			System.out.println("excel导出成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
