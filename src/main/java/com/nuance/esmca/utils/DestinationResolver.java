package com.nuance.esmca.utils;

import com.nuance.esmca.dataobjs.SecurityGroup;


public class DestinationResolver {

	public Class resolveDestination(String source) {
		if(EscriptionMessageType.SECURITY_GROUP.name().equals(source)) {
			return SecurityGroup.class;
		}
		return null;
	}
}
