package org.suggs.katas.jmsbroker.utils;

import static org.slf4j.LoggerFactory.getLogger;
import static org.suggs.katas.jmsbroker.SocketFinder.findNextAvailablePortBetween;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.suggs.katas.jmsbroker.broker.JMSMessageBroker;
import org.suggs.katas.jmsbroker.connection.JMSConfig;
import org.suggs.katas.jmsbroker.connection.JMSMessageBrokerFactory;
import org.suggs.katas.jmsbroker.constants.Constants;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;
import org.suggs.katas.jmsbroker.exception.NoMessageReceivedException;
import org.suggs.katas.jmsbroker.exception.NoMessageSendException;
import org.suggs.katas.jmsbroker.operations.Receiver;
import org.suggs.katas.jmsbroker.operations.Sender;

public class JMSUtils {

	private JMSMessageBroker jmsBroker;
	private Connection connection;
	private Session session;
	private Destination queue;
	private JMSConfig jmsConfig;
	private Sender sender;
	private Receiver receiver;
	private ApplicationContext context;
	private int timeOut;
	private BrokerService  brokerService;
	private MessageProducer messageProducer;
	private MessageConsumer messageConsumer;


	private static final Logger LOG = getLogger(JMSUtils.class);

	public static String getActivePortURL() {
		return Constants.DEFAULT_BROKER_URL_PREFIX + findNextAvailablePortBetween(41616, 50000);
	}

	private JMSUtils() {
		// Initializing Spring context
		context = new ClassPathXmlApplicationContext("Spring.xml");
		jmsConfig = (JMSConfig) context.getBean("JMSConfig");

	}

	public JMSUtils(JMSBrokerType brokerType, String brokerURL) {
		this();
		try {
			jmsBroker = JMSMessageBrokerFactory.getMessageBrokerFactory(brokerType, context);
			brokerService = jmsBroker.startEmbeddedBroker(brokerURL);
			connection = jmsConfig.createConnectionForBroker(brokerType, brokerURL);
			connection.start();
			session = jmsConfig.createSessionForBroker(connection);

		} catch (Exception e) {
			dispose();
			LOG.error("Exception ocuured while initializing JMS server : " + e.getMessage());
		}
	}


	public void initializeSender(String queueName){
		createJMSQueue(queueName);
		sender = (Sender) context.getBean("sender");
		try{
			messageProducer = jmsConfig.createMessageProducer(session, queue);
		}
		catch (Exception e) {
			dispose();
			LOG.error("Exception ocuured while creating message producer : " + e.getMessage());
		}

	}

	public void initializeReceiver(String queueName){
		createJMSQueue(queueName);
		receiver = (Receiver) context.getBean("receiver");
		receiver.setTimeOut(timeOut);
		try {
			messageConsumer = jmsConfig.createMessageConsumer(session, queue);
		} catch (JMSException e) {
			dispose();
			LOG.error("Exception ocuured while creating message consumer : " + e.getMessage());
		}
	}

	// We can change destination queue by calling this method by reusing same
	// session and connection
	private void createJMSQueue(String queueName) {
		if (queueName == null || queueName.trim().equals("")) {
			LOG.error("Queue Not Set ...");
		} else {
			try {
				queue = jmsConfig.createQueueForBroker(session, queueName);
			}
			catch (Exception e) {
				LOG.error("Exception ocuured while creating queue : " + e.getMessage());
			}
		}
	}

	public void sendMessage(String message) {

		try {
			sender.sendMessage(messageProducer, session, message);
			LOG.info("Message : \"" + message + "\" Send successfully");

		} catch (Exception e) {
			throw new NoMessageSendException("Unable to send message to Queue " + e.getMessage());
		}
	}

	public Message receiveMessages() {

		try {
			Message message = receiver.receiveMessage(messageConsumer, session);
			LOG.info("Message : \"" + ((TextMessage) message).getText() + "\" received successfully");
			return message;

		} catch (Exception e) {
			throw new NoMessageReceivedException("Unable to receive message from Queue " + e.getMessage());
		}
	}


	public void dispose() {
		try {
			if(session != null)
				session.close();
			if(connection != null)
				connection.close();
			if(jmsBroker != null)
				jmsBroker.stopEmbeddedBroker();
		} catch (Exception e) {
			LOG.error("Exception occured while disposing resource");
		}
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public BrokerService getBrokerService() {
		return brokerService;
	}

	public void setBrokerService(BrokerService brokerService) {
		this.brokerService = brokerService;
	}
}
