package indi.wzl.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtil {
	/**
	 * 创建对象
	 * @param className
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object createClassObject(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class c = Class.forName(className);
		Object b = c.newInstance();
		return b;
	}
	
	/**
	 *调用对象的get方法 ֵ
	 * @param <T>
	 * @param obj
	 * @param filedName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	public static Object getProperty(Object obj,String filedName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class aClass =  obj.getClass();
		//Field field= c.getField(filedName);
		//Object val = field.get(obj);
		
		//转换首字母大写
		filedName = filedName = StringUtil.converFirstCapital(filedName);
		
		String MethodName = "get"+filedName;
		Method method  = aClass.getMethod(MethodName,new Class[]{});
		
		//obj为null时执行的是静态方法
		Object val = method.invoke(obj, new Class[]{});
		return val;
	}
	
	/**
	 *ֵ 调用对象的set方法
	 * @param obj
	 * @param filedName
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	public static void setProperty(Object obj,String filedName,Object...args) throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class c = obj.getClass();
		Field field = c.getDeclaredField(filedName);
		
		//转换首字母大写
		filedName = StringUtil.converFirstCapital(filedName);
		String MethodName = "set"+filedName;
		Class[] argsClass = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			argsClass[i] = field.getType();
		}
		Method method = c.getMethod(MethodName,argsClass);
		method.invoke(obj,args);
	}
	
	/**
	 * 获取（类、字段）的第一个annotation的方法执行结果
	 * @param cla
	 * @param fieldName cla类的某个字段名,为空时取的是cla的annotation的MethodName的值
	 * @param MethodName
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException 
	 */
	public static <T> T getAnnotationMethodValue(Class cla,String fieldName,String MethodName,Object...args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		Class annotationCla = null;
		Annotation annotation = null;
		if(StringUtil.isNull(fieldName)){
			//类的annotation
			Annotation[] annotations = cla.getAnnotations();
			annotation = annotations[0];
			if(annotation == null){
				return null;
			}
			annotationCla = annotation.getClass();
		}else{
			//字段的annotation
			Field field = cla.getDeclaredField(fieldName);
			Annotation[] annotations = field.getAnnotations();
			annotation = annotations[0];
			if(annotation == null){
				return null;
			}
			annotationCla = annotation.getClass();
		}
		Class[] argsClass = null;
		if(args != null){
			argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {     
	            argsClass[i] = args[i].getClass();     
	        }
		}
         
		Method method = annotationCla.getMethod(MethodName, argsClass);
		T t = (T) method.invoke(annotation, args);
		return t;
	}
	
	/**
	 * 获取（类、字段）的annotation的方法执行结果
	 * @param <A>
	 * @param cla
	 * @param fieldName cla类的某个字段名,为空时取的是cla的annotation的MethodName的值
	 * @param acla 指定类型的annotaion的class
	 * @param MethodName
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException 
	 */
	public static <T, A extends Annotation> T getAnnotationMethodValue(Class cla,String fieldName,Class<A> acla,String MethodName,Object...args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		Annotation annotation = null;
		Class annotationCla = null;
		if(StringUtil.isNull(fieldName)){
			//类的annotation
			annotation =cla.getAnnotation(acla);
			if(annotation == null){
				return null;
			}
			annotationCla = annotation.getClass();
		}else{
			//字段的annotation
			Field field = cla.getDeclaredField(fieldName);
			annotation = field.getAnnotation(acla);
			if(annotation == null){
				return null;
			}
			annotationCla = annotation.getClass();
		}
		Class[] argsClass = null;
		if(args != null){
			argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {     
	            argsClass[i] = args[i].getClass();     
	        }
		}
         
		Method method = annotationCla.getMethod(MethodName, argsClass);
		T t = (T) method.invoke(annotation, args);
		return t;
	}
	
	/**
	 * 获取annotation的方法执行结果
	 * @param annotation
	 * @param MethodName
	 * @param args
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static <T> T getAnnotationMethodValue(Annotation annotation,String MethodName,Object...args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class annotationCla = annotation.getClass();
		Class[] argsClass = null;
		if(args != null){
			argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {     
	            argsClass[i] = args[i].getClass();     
	        }
		}
         
		Method method = annotationCla.getMethod(MethodName, argsClass);
		T t = (T) method.invoke(annotation, args);
		return t;
	}
	
	/**
	 * 将obj转换成type类型的对象
	 * @param type
	 * @param obj
	 * @return
	 */
	public static  <T> T castObj(Class<T> type,Object obj){
		return type.cast(obj);
	}
}
