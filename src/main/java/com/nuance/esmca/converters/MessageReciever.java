package com.nuance.esmca.converters;

public class MessageReciever {

	public String receive(String message) {
		System.out.println("Message Received :" + message);
		return message;
	}
}
