package org.suggs.katas.jmsbroker.operations;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.stereotype.Component;
import org.suggs.katas.jmsbroker.constants.Constants;
import org.suggs.katas.jmsbroker.exception.NoMessageReceivedException;

/**
 *
 * Generic class to receive message from queue
 *
 * @author Sanjeev_Bharti
 *
 */
@Component
public class Receiver {

	private int timeOut;

	public Message receiveMessage(MessageConsumer messageConsumer, Session session) throws Exception {

		Message jmsMessage = messageConsumer.receive(timeOut == 0 ? Constants.DEFAULT_RECEIVE_TIMEOUT : timeOut);
		if (jmsMessage == null) {
			throw new NoMessageReceivedException(
					String.format("No messages received from the broker within the %d timeout", timeOut));
		}
		return jmsMessage;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

}
