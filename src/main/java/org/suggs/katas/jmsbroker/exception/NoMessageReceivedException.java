package org.suggs.katas.jmsbroker.exception;

public class NoMessageReceivedException extends RuntimeException {
	public NoMessageReceivedException(String reason) {
		super(reason);
	}
}