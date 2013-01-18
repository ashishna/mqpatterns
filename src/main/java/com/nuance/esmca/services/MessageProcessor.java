package com.nuance.esmca.services;


public interface MessageProcessor<T> {

	public void process(T messageType);
	//public void registerMessageConverter(MessageConverter mc);
}
