package com.dev.bbs.exceptions;

public class UpdateFailedException extends RuntimeException{

	public UpdateFailedException(String string)
	{
		System.err.println(string);
	}
}
