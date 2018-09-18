package com.ryan.exception;

/**
 * 自定义异常类
 * @author YoriChen
 * @date 2018/6/21
 */
public class CustomException extends Exception {

	public String message;
	
	public CustomException(String message){
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
