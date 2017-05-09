package org.suggs.katas.jmsbroker.connection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;


/**
 *
 * Class to have JMS config level settings
 * @author Sanjeev_Bharti
 *
 */
@Component
public class JMSConfig {

	private ConnectionFactory connectionFactory;
	private Connection connection;

	public Connection createConnectionForBroker(JMSBrokerType jmsBrokerType, String brokerUrl) throws JMSException {

		connectionFactory = new JmsBrokerConnectionFactory().getConnectionFactory(jmsBrokerType, brokerUrl);
		connection = connectionFactory.createConnection();
		return connection;
	}

	public Session createSessionForBroker(Connection connection) throws JMSException {
		if(connection != null)
			return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return null;
	}

	public Destination createQueueForBroker(Session session, String queueName) throws JMSException {
		if(session != null)
			return session.createQueue(queueName);
		return null;
	}

	public TextMessage getTextMessage(Session session,String message) throws JMSException {
		if(session != null)
			return session.createTextMessage(message);
		return null;
	}

	public MessageProducer createMessageProducer(Session session, Destination queue) throws JMSException {
		if(session != null)
			return session.createProducer(queue);
		return null;
	}

	public MessageConsumer createMessageConsumer(Session session, Destination queue) throws JMSException {
		if(session != null)
			return session.createConsumer(queue);
		return null;
	}


}
