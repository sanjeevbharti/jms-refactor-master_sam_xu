package org.suggs.katas.jmsbroker;

import static org.slf4j.LoggerFactory.getLogger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.suggs.katas.jmsbroker.constants.Constants;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;
import org.suggs.katas.jmsbroker.utils.ActiveMQJMSUtils;
import org.suggs.katas.jmsbroker.utils.JMSUtils;

public class JmsMessageBrokerSupport {
	private static final Logger LOG = getLogger(JmsMessageBrokerSupport.class);
	private String brokerUrl;
	private static JMSUtils jmsUtils;

	private JmsMessageBrokerSupport(String aBrokerUrl) {
		brokerUrl = aBrokerUrl;
	}

	public static JmsMessageBrokerSupport createARunningEmbeddedBrokerOnAvailablePort() throws Exception {
		String aBrokenUrl = ActiveMQJMSUtils.getActivePortURL();
		jmsUtils = new JMSUtils(JMSBrokerType.ACTIVE_MQ, ActiveMQJMSUtils.getActivePortURL());
		JmsMessageBrokerSupport broker = bindToBrokerAtUrl(aBrokenUrl);
		return broker;
	}

	public void stopTheRunningBroker() throws Exception {
		jmsUtils.dispose();
	}

	public static JmsMessageBrokerSupport bindToBrokerAtUrl(String aBrokerUrl) throws Exception {
		return new JmsMessageBrokerSupport(aBrokerUrl);
	}

	public final JmsMessageBrokerSupport andThen() {
		return this;
	}

	public final String getBrokerUrl() {
		return brokerUrl;
	}

	public JmsMessageBrokerSupport sendATextMessageToDestinationAt(String aDestinationName, final String aMessageToSend)
			throws JMSException {

		jmsUtils.initializeSender(aDestinationName);
		jmsUtils.sendMessage(aMessageToSend);
		return this;
	}

	public String retrieveASingleMessageFromTheDestination(String aDestinationName) throws Exception {
		return retrieveASingleMessageFromTheDestination(aDestinationName, Constants.DEFAULT_RECEIVE_TIMEOUT);
	}

	public String retrieveASingleMessageFromTheDestination(String aDestinationName, final int aTimeout)
			throws Exception {

		jmsUtils.initializeReceiver(aDestinationName);
		Message message = jmsUtils.receiveMessages();
		return ((TextMessage) message).getText();
	}

	public long getEnqueuedMessageCountAt(String aDestinationName) throws Exception {
		return ActiveMQJMSUtils.getDestinationStatisticsFor(jmsUtils.getBrokerService(), aDestinationName, brokerUrl)
				.getMessages().getCount();
	}

	public boolean isEmptyQueueAt(String aDestinationName) throws Exception {
		return ActiveMQJMSUtils.getEnqueuedMessageCountAt(jmsUtils.getBrokerService(), aDestinationName,
				brokerUrl) == 0;
	}

}
