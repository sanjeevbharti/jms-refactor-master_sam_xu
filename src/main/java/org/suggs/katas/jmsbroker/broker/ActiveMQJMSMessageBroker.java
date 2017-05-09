package org.suggs.katas.jmsbroker.broker;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQJMSMessageBroker implements JMSMessageBroker {

	private BrokerService brokerService;

	public ActiveMQJMSMessageBroker() throws Exception {
		brokerService = new BrokerService();
	}

	public BrokerService getBrokerService() {
		return brokerService;
	}

	public void setBrokerService(BrokerService brokerService) {
		this.brokerService = brokerService;
	}

	@Override
	public BrokerService startEmbeddedBroker(String brokerUrl) throws Exception {

		brokerService.setPersistent(false);
		if (brokerUrl != null) {
			brokerService.addConnector(brokerUrl);
		}
		brokerService.start();
		return brokerService;
	}

	@Override
	public void stopEmbeddedBroker() throws Exception {
		if (brokerService == null) {
			throw new IllegalStateException("Cannot stop the broker from this API: "
					+ "perhaps it was started independently from this utility");
		}
		brokerService.stop();
		brokerService.waitUntilStopped();
	}

	@Override
	public Broker getRegionBroker() {
		return brokerService.getRegionBroker();
	}
}
