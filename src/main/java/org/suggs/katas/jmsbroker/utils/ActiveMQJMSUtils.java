package org.suggs.katas.jmsbroker.utils;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;

public class ActiveMQJMSUtils extends JMSUtils {

	public ActiveMQJMSUtils(JMSBrokerType brokerType, String brokerURL) {
		super(brokerType, brokerURL);
	}

	public static long getEnqueuedMessageCountAt(BrokerService brokerService, String aDestinationName, String brokerUrl)
			throws Exception {
		return getDestinationStatisticsFor(brokerService, aDestinationName, brokerUrl).getMessages().getCount();
	}

	public static boolean isEmptyQueueAt(BrokerService brokerService, String aDestinationName, String brokerUrl)
			throws Exception {
		return getEnqueuedMessageCountAt(brokerService, aDestinationName, brokerUrl) == 0;
	}

	public static DestinationStatistics getDestinationStatisticsFor(BrokerService brokerService,
			String aDestinationName, String brokerUrl) throws Exception {
		Broker regionBroker = brokerService.getRegionBroker();
		for (org.apache.activemq.broker.region.Destination destination : regionBroker.getDestinationMap().values()) {
			if (destination.getName().equals(aDestinationName)) {
				return destination.getDestinationStatistics();
			}
		}
		throw new IllegalStateException(
				String.format("Destination %s does not exist on broker at %s", aDestinationName, brokerUrl));
	}

}
