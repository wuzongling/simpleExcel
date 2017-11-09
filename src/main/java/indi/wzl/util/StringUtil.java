package indi.wzl.util;

/**
 * 字符串处理工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	/**
	 * 转换首字母大写
	 * @param str
	 * @return
	 */
	public static String converFirstCapital(String str){
		char[] b = str.toCharArray();
		   if (b[0] >= 97 && b[0] <= 122) {
		    b[0] = (char) (b[0] - 32);
		   }
		   return new String(b);
	}
	
	/**
	 * 是否为Null
	 * @param str
	 * @return
	 */
	public static Boolean isNull(String str){
		boolean b = false;
		str = str.trim();
		if(("".equals(str)||""==str)||str == null){
			b = true;
		}
		return b;
	}

	/**
	 * 是否不为Null
	 * @param str
	 * @return
	 */
	public static Boolean isNotNull(String str){
		boolean b = true;
		str = str.trim();
		if(("".equals(str)||""==str)||str == null){
			b = false;
		}
		return b;
	}
	
	/**
	 * 将obj对象转换成int格式的字符串（去掉小数点）
	 * @param obj
	 * @return
	 */
	public static String castIntegerStr(Object obj){
		String objStr = obj.toString();
		int point = objStr.indexOf(".");
		if(point!=-1){
			System.out.println(point);
			objStr = objStr.substring(0, point);
		}
		return objStr;
	}
}
