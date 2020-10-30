package com.example.webchat.exception;

public class WebchatRepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4016770044220777608L;
	
	public WebchatRepositoryException() {
		super("Fail to generated NickName");
	}
	public WebchatRepositoryException(String message) {
		super(message);
	}
}
