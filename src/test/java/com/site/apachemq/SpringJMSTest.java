package com.site.apachemq;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.broker.BrokerService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nuance.esmca.config.JMSConfiguration;
import com.nuance.esmca.dataobjs.SecurityGroup;
import com.nuance.esmca.services.MessageSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JMSConfiguration.class})
public class SpringJMSTest {
	
	@Autowired
	private  JmsTemplate jmsTemplate;
	@Autowired
	private MessageSender jmsMessageService;
	@Autowired
	private SecurityGroup securityGroup;
	//private static BrokerService broker;
	
	@BeforeClass
	public static void initialize() throws Exception {
		/*broker = new BrokerService();
		broker.setUseJmx(true);
		broker.addConnector("tcp://localhost:61616");

		broker.start();*/
	}
	
	@Test
	public void testJMSConfiguration()  {
		Assert.assertNotNull(jmsTemplate);
	}
	
	@Test
	public void testJMSSend() throws JMSException  {
		final String messageSent = "hello queue world";
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				System.out.println("About to send message");
				return session.createTextMessage(messageSent);
				}
			});
		
	}
	
	@Test
	public void testMessageService() throws JMSException  {
		final String message = "hello queue world";
		jmsMessageService.sendMessage(message);
		
	}
	
	@Test
	public void testMessageConversion() throws JMSException  {
		
		securityGroup.setInstitutionName("My Institution");
		securityGroup.setName("Administrator");
		securityGroup.setPermissionSetName("Grant All");
		
		
		jmsMessageService.sendMessage(securityGroup);
		
	}
	@AfterClass
	public static void cleanUp() throws Exception {
		//broker.stop();
	}

}
