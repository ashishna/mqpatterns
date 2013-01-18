package com.site.apachemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import junit.framework.Assert;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApacheMQTest {

	private static ActiveMQConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;
	private static BrokerService broker;
	private static boolean transacted = false;

	@BeforeClass
	public static void startMQServer() throws Exception {
		broker = new BrokerService();
		broker.setUseJmx(true);
		broker.addConnector("tcp://localhost:61616");

		broker.start();
		setUp();

	}

	private static void setUp() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection
				.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("eScriptionPush");
	}

	@AfterClass
	public static void stopJMS() throws Exception {
		broker.stop();
	}

	@Test
	public void testMQConnection() {
		Assert.assertNotNull(connection);
	}
	
	@Test
	public void testAbleToProduceAndConsume() throws JMSException {
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("eScriptionPush");
        MessageProducer producer = session.createProducer(queue);
        TextMessage msg = session.createTextMessage();
        msg.setText("Hello JMS World");
        producer.send(msg);
        MessageConsumer consumer = session.createConsumer(queue);
        
        TextMessage receive = (TextMessage) consumer.receive();
        Assert.assertEquals("Hello JMS World", receive.getText());
	}
}
