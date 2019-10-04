package com.dev.bbs.exceptions;

public class TicketRetrievalFailedException extends RuntimeException{

	public TicketRetrievalFailedException(String string)
	{
		System.err.println(string);
	}
}
