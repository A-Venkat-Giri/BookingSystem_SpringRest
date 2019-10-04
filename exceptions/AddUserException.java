package com.dev.bbs.exceptions;

public class AddUserException extends RuntimeException{

	public AddUserException(String string)
	{
		System.err.println(string);
	}
}
