package org.suggs.katas.jmsbroker.operations;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

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
public class ReceiverTest {
	@InjectMocks
	Receiver receiver;

	@Mock
	JMSConfig jmsConfig;

	@Mock
	MessageConsumer messageConsumer;

	@Mock
	Message message;

	@Mock
	Session session;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		receiver.setTimeOut(5);
	}

	@Test
	public void testReceiveMessage() throws Exception {

		Mockito.doReturn(messageConsumer).when(jmsConfig).createMessageConsumer(Mockito.any(Session.class), Mockito.any(Destination.class));

		Mockito.doReturn(message).when(messageConsumer).receive(Mockito.anyInt());

		message = receiver.receiveMessage(messageConsumer,session);

		Mockito.verify(messageConsumer, Mockito.times(1)).receive(Matchers.anyInt());

	}

}
