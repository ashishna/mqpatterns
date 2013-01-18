package com.nuance.esmca.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtils {

	 
	public static final <T> String MarshalToXml(T source) throws Exception {
		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(source.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		OutputStream output = null;
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
	    
		jaxbMarshaller.marshal(source, output);
		return output.toString();
	}
	
	public static final <T> T unMarshalToObject(String messageContents, T destination) throws Exception {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(destination.getClass());
		InputStream is = new ByteArrayInputStream(messageContents.getBytes());
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		destination = (T) jaxbUnmarshaller.unmarshal(is);
		
		return destination;
	}
}
