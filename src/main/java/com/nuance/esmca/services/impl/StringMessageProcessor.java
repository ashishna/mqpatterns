package com.nuance.esmca.services.impl;


import com.nuance.esmca.services.MessageProcessor;

public class StringMessageProcessor implements MessageProcessor<String> {

	public  void process(String messageType) {
		System.out.println("Message Received from Queue :: \n" + messageType);
	}

}
