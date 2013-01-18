package com.nuance.esmca.converters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.nuance.esmca.dataobjs.SecurityGroup;

public class DocumentMessgaeConverter implements MessageConverter {

	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		
		SecurityGroup securityGroup = (SecurityGroup) object;
		
		OutputStream output = null;
	    
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(SecurityGroup.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			output = new OutputStream() {
				
		        private StringBuilder string = new StringBuilder();
		        
		        @Override
		        public void write(int b) throws IOException {
		            this.string.append((char) b );
		        }

		        public String toString(){
		            return this.string.toString();
		        }
		    };
		    
			jaxbMarshaller.marshal(securityGroup, output);
					
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session.createTextMessage(output.toString());
	}

	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		TextMessage textMessage = (TextMessage) message;
		String messageContents = textMessage.getText();
		SecurityGroup securityGroup = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(SecurityGroup.class);
			InputStream is = new ByteArrayInputStream(messageContents.getBytes());
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			securityGroup = (SecurityGroup) jaxbUnmarshaller.unmarshal(is);
			//securityGroup.registerMessageConverter();
			System.out.println("Customer Obj "+ securityGroup.getInstitutionName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return securityGroup;
	}

}
