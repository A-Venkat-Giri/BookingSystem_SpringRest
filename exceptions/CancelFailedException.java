package com.dev.bbs.exceptions;

public class CancelFailedException extends RuntimeException{

	public CancelFailedException(String string)
	{
		System.err.println(string);
	}
}
