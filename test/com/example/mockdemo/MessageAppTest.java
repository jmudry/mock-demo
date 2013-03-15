package com.example.mockdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageAppTest {

	private static final String INVALIDSERWER = "invalidserwer";
	private static final String INVALIDRECEIPIENT = "invalidreceipient";
	private static final String VALID_CONTENT = "validContent";
	private static final String INVALID_CONTENT = "invalidContent";
	private static final String VALIDRECEIPIENT = "validreceipient";
	private static final String VALIDSERWER = "validserwer";

	@Test
	public void checkSending () {
		InvocationHandler ih = new MassageServiceHandler();
		MessageService ms = (MessageService)Proxy.newProxyInstance(
				MessageService.class.getClassLoader(), new Class[]{ MessageService.class}, ih);
				
		
		MessageApp mapp = new MessageApp(ms);	
		
		assertEquals(0, mapp.sendMessage(VALIDSERWER, VALIDRECEIPIENT, VALID_CONTENT));
		assertEquals(2, mapp.sendMessage(INVALIDSERWER, VALIDRECEIPIENT, VALID_CONTENT));
		assertEquals(4, mapp.sendMessage(VALIDSERWER, INVALIDRECEIPIENT, VALID_CONTENT));
		assertEquals(3, mapp.sendMessage(VALIDSERWER, VALIDRECEIPIENT, INVALID_CONTENT));
		assertEquals(3, mapp.sendMessage(VALIDSERWER, INVALIDRECEIPIENT, INVALID_CONTENT));
	}
	
	class MassageServiceHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if ("connect".equals(method.getName())) {
				if (VALIDSERWER.equals(args[0].toString())){
					return MessageStatus.CONNECTED;
				} else {
					return MessageStatus.CONNECTION_ERROR;
				}
			} 
			
			if ("sendMessage".equals(method.getName())) {
				if (INVALID_CONTENT.equals(args[1].toString())) {
					return MessageStatus.INVALID_CONTENT;
				} else if (INVALIDRECEIPIENT.equals(args[0].toString())){
					return MessageStatus.INVALID_RECEIPIENT;
				} else {
					return MessageStatus.SEND;
				}
			}
			
			return MessageStatus.CONNECTION_ERROR;
		}
		
	}
	
}


