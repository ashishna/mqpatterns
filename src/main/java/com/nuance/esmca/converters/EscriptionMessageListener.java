package com.nuance.esmca.converters;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.nuance.esmca.dataobjs.SecurityGroup;
import com.nuance.esmca.utils.JAXBUtils;

public class EscriptionMessageListener implements MessageListener {

	public void onMessage(Message message) {
		TextMessage message2 = (TextMessage) message;
		try {
			try {
				System.out.println(JAXBUtils.unMarshalToObject(message2.getText(), new SecurityGroup()).getInstitutionName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			String messageText = message2.getText();
			System.out.println("Message "+ messageText);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
