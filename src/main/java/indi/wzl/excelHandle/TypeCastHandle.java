package indi.wzl.excelHandle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import indi.wzl.Exception.SimpleExcelException;
import indi.wzl.annotation.DateFormat;
import indi.wzl.constant.TypeConstant;
import indi.wzl.util.ClassUtil;
import indi.wzl.util.StringUtil;

public class TypeCastHandle {
	//格式
	private static String format = "yyyy-MM-dd";
	
	private static SimpleDateFormat sdf = null;
	
	public static void annotationInit(Field field) throws SimpleExcelException{
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if(annotation.annotationType().equals(DateFormat.class)){
				//日期格式
				try {
					String val = ClassUtil.getAnnotationMethodValue(annotation, "value");
					if(null!= val && "".equals(val)){
						format = val;
					}
					sdf =   new SimpleDateFormat(format);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new SimpleExcelException("field annotation init faild", e);
				} 
			}else {
				sdf =   new SimpleDateFormat(format);
			}
		}
	}
	
	/**
	 * 将obj转换成field的类型
	 * @param obj
	 * @return
	 * @throws SimpleExcelException 
	 */
	public static <T> T typeCast(Field field,Object obj) throws SimpleExcelException{
		Class<T> type = (Class<T>) field.getType();
		int tp = getType(type);
		annotationInit(field);
		switch (tp) {
		case TypeConstant.INTEGET:
			return (T) integerCast(obj);
		case TypeConstant.LONG:
			return (T) LongCast(obj);
		case TypeConstant.BOOLEAN:
			return (T) booleanCast(obj);
		case TypeConstant.DOUBLE:
			return (T) doubleCast(obj);
		case TypeConstant.FLOAT:
			return (T) floatCast(obj);
		case TypeConstant.STRING:
			return  (T) stringCast(tp,obj);
		case TypeConstant.DATE:
			return (T)dateCast(obj,tp);
		}
		return ClassUtil.castObj(type, obj);
	}
	
	/**
	 * 获取类型
	 * @param type
	 * @return
	 */
	public static int getType(Class type){
		if(int.class.equals(type) || Integer.class.equals(type)){
			return TypeConstant.INTEGET;
		}else if(Long.class.equals(type) || long.class.equals(type)){
			return TypeConstant.LONG;
		}else if(Float.class.equals(type)|| float.class.equals(type)){
			return TypeConstant.FLOAT;
		}else if(Double.class.equals(type)||double.class.equals(type)){
			return TypeConstant.DOUBLE;
		}else if(Boolean.class.equals(type)|| boolean.class.equals(type)){
			return TypeConstant.BOOLEAN;
		}else if(String.class.equals(type)){
			return TypeConstant.STRING;
		}else if(Date.class.equals(type)){
			return TypeConstant.DATE;
		}else{
			//默认String
			return TypeConstant.NONE;
		}
	}
	
	/**
	 * 整形转换
	 * @param obj
	 * @return
	 */
	public static Integer integerCast(Object obj){
		//String s = StringUtil.castIntegerStr(obj);
		if(obj == null){
			return 0;
		}
		return Integer.valueOf(obj.toString());
	}
	
	/**
	 * long转换
	 * @param obj
	 * @return
	 */
	public static Long LongCast(Object obj){
		//String s = StringUtil.castIntegerStr(obj);
		if(obj == null){
			return 0L;
		}
		return Long.valueOf(obj.toString());
	}
	
	/**
	 * float转换
	 * @param obj
	 * @return
	 */
	public static Float floatCast(Object obj){
		if(obj == null){
			return 0.0f;
		}
		return Float.valueOf(obj.toString());
	}
	
	/**
	 * double 转换
	 * @param obj
	 * @return
	 */
	public static Double doubleCast(Object obj){
		if(obj == null){
			return 0.0d;
		}
		return Double.valueOf(obj.toString());
	}
	
	/**
	 * 布尔类型转换
	 * @param obj
	 * @return
	 */
	public static Boolean booleanCast(Object obj){
		if(obj == null){
			return false;
		}
		return Boolean.valueOf(obj.toString());
	}
	
	/**
	 * 字符串转换
	 * @param obj
	 * @return
	 */
	public static String stringCast(int type,Object obj){
		if(obj == null){
			return "";
		}
		if(getType(obj.getClass()) == TypeConstant.DATE){
			//日期转换
			return sdf.format((Date)obj);
		}
		return obj.toString();
	}
	
	public static Object dateCast(Object obj,int fieldType) throws SimpleExcelException{
		if(obj == null){
			return new Date();
		}
		if(getType(obj.getClass()) == TypeConstant.STRING){
			return strToDateCast(obj);
		}else if(getType(obj.getClass()) == TypeConstant.DATE){
			if(fieldType == TypeConstant.STRING)
				return dateToStrCast(obj);
			if(fieldType == TypeConstant.DATE)
				return obj;
		}
		return null;
	}

	/**
	 * 字符串转换date
	 * @param obj
	 * @return
	 * @throws SimpleExcelException
	 */
	public static Date strToDateCast(Object obj) throws SimpleExcelException{
			try {
				return sdf.parse(obj.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new SimpleExcelException("字符串转换日期失败", e);
			}
	}

	/**
	 * 日期转换字符串
	 * @param obj
	 * @return
	 * @throws SimpleExcelException
	 */
	public static String dateToStrCast(Object obj) throws SimpleExcelException{
		try {
			return sdf.format((Date)obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SimpleExcelException("日期转换字符串失败", e);
		}
	}
}	
