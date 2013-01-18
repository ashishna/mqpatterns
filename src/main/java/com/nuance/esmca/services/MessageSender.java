package com.nuance.esmca.services;

public interface MessageSender<T> {

	public  void sendMessage(T message);
	//public  String receive(String message);
}
