package org.suggs.katas.jmsbroker.exception;

public class NoMessageSendException extends RuntimeException{
	public NoMessageSendException(String reason) {
		super(reason);
	}

}
