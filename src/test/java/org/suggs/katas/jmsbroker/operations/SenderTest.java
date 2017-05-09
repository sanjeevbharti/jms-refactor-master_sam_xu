package org.suggs.katas.jmsbroker.operations;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.suggs.katas.jmsbroker.connection.JMSConfig;

@RunWith(MockitoJUnitRunner.class)
public class SenderTest {

	@InjectMocks
	Sender sender;

	@Mock
	JMSConfig jmsConfig;

	@Mock
	MessageProducer messageProducer;

	@Mock
	Session session;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test

	public void testSendMessage() throws JMSException {

		Mockito.doReturn(messageProducer).when(jmsConfig).createMessageProducer(Mockito.any(Session.class),
				Mockito.any(Destination.class));

		sender.sendMessage(messageProducer, session, "Test Message");

		Mockito.verify(messageProducer, Mockito.times(1)).send(Matchers.any(TextMessage.class));

	}

}
