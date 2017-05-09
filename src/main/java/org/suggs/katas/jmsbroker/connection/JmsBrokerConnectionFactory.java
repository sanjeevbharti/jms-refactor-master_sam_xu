package org.suggs.katas.jmsbroker.connection;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;


/**
 *
 * Factory method to return Broker specific Connection Factory
 * @author Sanjeev_Bharti
 *
 */
public class JmsBrokerConnectionFactory {

	public ConnectionFactory getConnectionFactory(JMSBrokerType jmsBrokerType, String brokerUrl) {
		if (jmsBrokerType == null) { //Default it return Active MQ Connection factory if not set
			return new ActiveMQConnectionFactory(brokerUrl);
		}
		if (JMSBrokerType.ACTIVE_MQ.getValue().equalsIgnoreCase(jmsBrokerType.getValue())) {
			return new ActiveMQConnectionFactory(brokerUrl);
		}
		return null;
	}

}
