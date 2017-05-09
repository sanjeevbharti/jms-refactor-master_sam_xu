package org.suggs.katas.jmsbroker.operations;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.suggs.katas.jmsbroker.connection.JMSConfig;

/**
 *
 * Generic class to send message from queue
 *
 * @author Sanjeev_Bharti
 *
 */
@Component
public class Sender {

	@Autowired
	private JMSConfig jmsConfig;


	public void sendMessage(MessageProducer messageProducer, Session session, String message) throws JMSException {

		messageProducer.send(jmsConfig.getTextMessage(session,message));
	}
}
