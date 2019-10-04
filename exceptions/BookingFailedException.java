package com.dev.bbs.exceptions;

public class BookingFailedException extends RuntimeException{

	public BookingFailedException(String string)
	{
		System.err.println(string);
	}
}
