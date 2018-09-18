package com.ryan.interceptor;

/**
 * 空异常
 * @author YoriChen
 * @date 2018/6/28
 */
public class NullException extends RuntimeException {

	public NullException(String message) {
		super(message);
	}

	public NullException(String message, Throwable cause) {
		super(message, cause);
	}

}
