package indi.wzl.Exception;

public class SimpleExcelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5448288224253006816L;
	
	public SimpleExcelException(String message, Throwable e){
		super(message,e);
	}
	
	public SimpleExcelException(String message){
		super(message);
	}
}
