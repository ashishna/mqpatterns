package com.nuance.esmca.dataobjs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;

import com.nuance.esmca.utils.EscriptionMessageType;

@XmlRootElement
public class SecurityGroup extends EscriptionMessage {

	@Autowired
	MessageConverter documentMessgaeConverter;
	
	private String name;
	private String institutionName;
	private String permissionSetName;
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	@XmlElement
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getPermissionSetName() {
	
		return permissionSetName;
	}
	@XmlElement
	public void setPermissionSetName(String permissionSetName) {
		this.permissionSetName = permissionSetName;
	}

	@Override
	public MessageConverter getDefaultMessageConverter() {
		return documentMessgaeConverter;
	}

	@Override
	public EscriptionMessageType getMessageType() {
		return EscriptionMessageType.SECURITY_GROUP;
	}
	
}
