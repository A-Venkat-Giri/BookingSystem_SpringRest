package com.dev.bbs.exceptions;

public class DeleteFailedException extends RuntimeException{

	public DeleteFailedException(String string)
	{
		System.err.println(string);
	}
}
