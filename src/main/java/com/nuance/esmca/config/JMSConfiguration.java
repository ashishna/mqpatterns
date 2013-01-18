package com.nuance.esmca.config;

import javax.jms.DeliveryMode;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;

import com.nuance.esmca.converters.EscriptionMessageListener;
import com.nuance.esmca.converters.DocumentMessgaeConverter;
import com.nuance.esmca.converters.MessageReciever;
import com.nuance.esmca.dataobjs.SecurityGroup;
import com.nuance.esmca.services.MessageProcessor;
import com.nuance.esmca.services.MessageSender;
import com.nuance.esmca.services.impl.EscriptionMessageSenderImpl;
import com.nuance.esmca.services.impl.StringMessageProcessor;

@Configuration
@PropertySource({ "classpath:/com/nuance/esmca/jms/test.jms.properties" })
public class JMSConfiguration {

	@Autowired
    private Environment env;
	
	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(env.getProperty("jms.broker.url"));
		return activeMQConnectionFactory;
	}
	
	@Bean
	public CachingConnectionFactory cachedConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setSessionCacheSize(Integer.parseInt(env.getProperty("jms.connection.cache.size")));
		cachingConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
		return cachingConnectionFactory;
		
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(cachedConnectionFactory());
		jmsTemplate.setDefaultDestination(queue());
		jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		jmsTemplate.setMessageConverter(documentMessgaeConverter());
		return jmsTemplate;
	}
	
	@Bean
	public ActiveMQQueue queue() {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue();
		activeMQQueue.setPhysicalName(env.getProperty("jms.queue.name"));
		return activeMQQueue;
	}
	
	@Bean
	public DefaultMessageListenerContainer defaultMessageListenerContainer() {
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setDestination(queue());
		defaultMessageListenerContainer.setConnectionFactory(cachedConnectionFactory());
		defaultMessageListenerContainer.setMessageListener(deaultMessageListener());
		return defaultMessageListenerContainer;
	}
	
	@Bean
	public EscriptionMessageListener deaultMessageListener() {
		return new EscriptionMessageListener();
	}
	@Bean
	public StringMessageProcessor stringMessageType() {
		StringMessageProcessor stringMessageType = new StringMessageProcessor();
		return stringMessageType;
	}
	
	@Bean
	public MessageProcessor messageType() {
		return stringMessageType();
	}
	
	/*@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
		messageListenerAdapter.setDefaultListenerMethod("receive");
		messageListenerAdapter.setDelegate(mapMessageReceiver());
		//messageListenerAdapter.setMessageConverter(documentMessgaeConverter());
		return messageListenerAdapter;
	}*/
	
	@Bean
	public MessageSender mapMessageSender() {
		return new EscriptionMessageSenderImpl();
	}
	@Bean
	public MessageReciever mapMessageReceiver() {
		return new MessageReciever();
	}
	@Bean
	
	public MessageConverter documentMessgaeConverter() {
		DocumentMessgaeConverter documentMessgaeConverter = new DocumentMessgaeConverter();
		return documentMessgaeConverter;
	}
	@Bean
	public SecurityGroup securityGroup() {
		SecurityGroup securityGroup = new SecurityGroup();
		return securityGroup;
	}
}

