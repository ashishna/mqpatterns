package com.nuance.esmca.dataobjs;

import org.springframework.jms.support.converter.MessageConverter;

import com.nuance.esmca.utils.EscriptionMessageType;

public abstract class EscriptionMessage {

	public abstract MessageConverter getDefaultMessageConverter();
	public abstract EscriptionMessageType getMessageType();
}
