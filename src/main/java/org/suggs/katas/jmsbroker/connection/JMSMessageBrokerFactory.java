package org.suggs.katas.jmsbroker.connection;

import org.springframework.context.ApplicationContext;
import org.suggs.katas.jmsbroker.broker.JMSMessageBroker;
import org.suggs.katas.jmsbroker.enums.JMSBrokerType;

public class JMSMessageBrokerFactory {

	public static JMSMessageBroker getMessageBrokerFactory(JMSBrokerType jmsBrokerType, ApplicationContext context) {
		if (jmsBrokerType == null) { //Default it return Active MQ Connection factory if not set
			return (JMSMessageBroker) context.getBean("activeMQJMSMessageBroker");
		}
		if (JMSBrokerType.ACTIVE_MQ.getValue().equalsIgnoreCase(jmsBrokerType.getValue())) {
			return (JMSMessageBroker) context.getBean("activeMQJMSMessageBroker");
		}
		return null;
	}

}
