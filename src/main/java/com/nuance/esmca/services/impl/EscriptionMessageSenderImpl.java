package com.nuance.esmca.services.impl;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import com.nuance.esmca.dataobjs.EscriptionMessage;
import com.nuance.esmca.dataobjs.JMSConstants;
import com.nuance.esmca.services.MessageSender;

public class EscriptionMessageSenderImpl implements MessageSender<EscriptionMessage> {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(final EscriptionMessage esMessage) {
		jmsTemplate.setMessageConverter(esMessage.getDefaultMessageConverter());
		jmsTemplate.convertAndSend(esMessage,  new MessagePostProcessor() {
			
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty(JMSConstants.MESSAGE_TYPE, esMessage.getMessageType().name());
				return message;
			}
		});
	}

		
	
}
