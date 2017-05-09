package org.suggs.katas.jmsbroker.enums;

public enum JMSBrokerType {

	ACTIVE_MQ("ACTIVE_MQ"),
	IBM_MQ("IBM_MQ");

	private String value;

	private JMSBrokerType(String val){
		value = val;
	}

	public String getValue() {
		return value;
	}

}
