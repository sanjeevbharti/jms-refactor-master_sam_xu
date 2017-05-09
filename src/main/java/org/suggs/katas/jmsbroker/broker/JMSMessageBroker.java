package org.suggs.katas.jmsbroker.broker;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;

public interface JMSMessageBroker {

	BrokerService startEmbeddedBroker(String brokerUrl) throws Exception;

	void stopEmbeddedBroker() throws Exception;

	Broker getRegionBroker();

}
