simpleExcel工具
========
# 一、简介

## 1.1 概述
simpleExcel工具是一款基于java做的一款java bean格式数据和excel表格之间相互转换的工具。

## 1.2 特性
* 1.使用方便快捷，整个过程只需1-2行代码。
* 2.配置简单，无需配置文件，直接在类上加注解

## 1.3 发展

## 1.4 下载
        源码字段：https://github.com/wuzongling/simpleExcel
        中央仓库地址：暂时未上传到中央仓库

# 二、快速入门

## 2.1 相关注解
* 1.@ExcelCell  excel表格单元格所处的第几列
* 2.@HeadName   excel表格列对应的表头
* 3.@DateFormat 日期格式转换

## 2.2 解析excel表格
[SignUp.java](https://github.com/wuzongling/simpleExcel/blob/master/src/test/java/indi/wzl/test/bean/SignUp.java)
``` java
public void testParse(){
		try {
			List list = (List)SimpleExcelFactory.readExcel("D://b.xlsx", SignUp.class);
			//InputStream is = HttpUtil.download("http://7u2hs1.com1.z0.glb.clouddn.com/4bd0007cbe7c5290541042bf0e60aa35-excelTest.xlsx");
			//List list = (List)SimpleExcelFactory.readXls(is, ExcelTest.class);
			System.out.println(JSON.toJSON(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
```
## 2.3 导出excel表格
``` java
public  void testExport(){
		try
		{
			OutputStream out = new FileOutputStream("D://b.xlsx");
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
```

## 2.4 动态导出excel表格
    动态导出通过fileName数组参数指定要导出的字段
    此时@ExcelCell注解失效，顺序由数组顺序决定
``` java
public void testDynamicExport(){
		try
		{
			OutputStream out = new FileOutputStream("D://b.xlsx");
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
```