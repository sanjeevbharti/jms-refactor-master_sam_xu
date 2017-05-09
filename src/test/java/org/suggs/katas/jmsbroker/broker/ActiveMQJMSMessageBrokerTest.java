package org.suggs.katas.jmsbroker.broker;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.suggs.katas.jmsbroker.utils.JMSUtils;

@RunWith(MockitoJUnitRunner.class)
public class ActiveMQJMSMessageBrokerTest {

	@Mock
	BrokerService brokerService;
	@Mock
	TransportConnector transportConnector;
	@InjectMocks
	ActiveMQJMSMessageBroker jmsMessageBroker;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testStartEmbeddedBroker() throws Exception {

		jmsMessageBroker.startEmbeddedBroker(JMSUtils.getActivePortURL());
		Mockito.verify(brokerService, Mockito.times(1)).start();
	}

	@Test
	public void testStopEmbeddedBroker() throws Exception {

		jmsMessageBroker.stopEmbeddedBroker();
		Mockito.verify(brokerService, Mockito.times(1)).stop();
	}

}
