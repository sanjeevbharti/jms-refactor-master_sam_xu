package org.suggs.katas.jmsbroker;

import static org.assertj.core.api.Assertions.assertThat;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.broker.BrokerService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.suggs.katas.jmsbroker.broker.JMSMessageBroker;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;
import org.suggs.katas.jmsbroker.exception.NoMessageReceivedException;
import org.suggs.katas.jmsbroker.utils.ActiveMQJMSUtils;
import org.suggs.katas.jmsbroker.utils.JMSUtils;

public class JmsMessageBrokerSupportTest_New {

	public static final String TEST_QUEUE = "MY_TEST_QUEUE_NEW";
	public static final String MESSAGE_CONTENT = "New Lorem blah blah";
	static JMSMessageBroker jmsBroker;
	static String brokerURL;
	static BrokerService brokerService;
	private static JMSUtils jmsUtils;

	@BeforeClass
	public static void setup() throws Exception {

		brokerURL = ActiveMQJMSUtils.getActivePortURL();
		jmsUtils = new JMSUtils(JMSBrokerType.ACTIVE_MQ,brokerURL);
		jmsUtils.initializeSender(TEST_QUEUE);
		jmsUtils.initializeReceiver(TEST_QUEUE);
	}

	@AfterClass
	public static void teardown() throws Exception {
		jmsUtils.dispose();
	}

	@Test
	public void sendsMessagesToTheRunningBroker() throws Exception {

		jmsUtils.sendMessage(MESSAGE_CONTENT);
		long messageCount = ActiveMQJMSUtils.getEnqueuedMessageCountAt(jmsUtils.getBrokerService(), TEST_QUEUE, brokerURL);
		assertThat(messageCount).isEqualTo(1);
	}

	@Test
	public void readsMessagesPreviouslyWrittenToAQueue() throws Exception {

		jmsUtils.sendMessage(MESSAGE_CONTENT);


		Message receivedMessage = jmsUtils.receiveMessages();
		assertThat(((TextMessage) receivedMessage).getText()).isEqualTo(MESSAGE_CONTENT);
	}

	@Test(expected = NoMessageReceivedException.class)
	public void throwsExceptionWhenNoMessagesReceivedInTimeout() throws Exception {
		jmsUtils.setTimeOut(1);

		Message receivedMessage = jmsUtils.receiveMessages();
	}
}