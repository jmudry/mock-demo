package com.example.mockdemo;

import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class EasyMockTest {

	
	private static final String CONTENT = "content";
	private static final String TO = "to";
	private static final String SERVER = "server";
	private MessageApp ma;
	private MessageService ms;
	
	@Before
	public void setUP(){
		ms = createMock(MessageService.class);
		ma = new MessageApp(ms);
	}
	
	@Test
	public void sendingOkCheck() {
		expect(ms.connect(SERVER)).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage(TO, CONTENT)).andReturn(MessageStatus.SEND);
		replay(ms);
		
		assertEquals(0, ma.sendMessage(SERVER, TO, CONTENT));
		verify(ms);	
	}
	
	@Test
	public void sendingNotConnectedCheck() {
		expect(ms.connect(SERVER)).andReturn(MessageStatus.CONNECTION_ERROR);
		replay(ms);
		
		assertEquals(2, ma.sendMessage(SERVER, TO, CONTENT));
		verify(ms);
	}
	
	@Test
	public void sendingErrorCheck() {
		expect(ms.connect(SERVER)).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage(TO, CONTENT)).andReturn(MessageStatus.SENDING_ERROR);
		replay(ms);
		
		assertEquals(1, ma.sendMessage(SERVER, TO, CONTENT));
		verify(ms);	
	}
	
	@Test
	public void sendingErrorReceipientCheck() {
		expect(ms.connect(SERVER)).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage(TO, CONTENT)).andReturn(MessageStatus.INVALID_RECEIPIENT);
		replay(ms);
		
		assertEquals(4, ma.sendMessage(SERVER, TO, CONTENT));
		verify(ms);	
	}
	
	@Test
	public void sendingErrorContentCheck() {
		expect(ms.connect(SERVER)).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage(TO, CONTENT)).andReturn(MessageStatus.INVALID_CONTENT);
		replay(ms);
		
		assertEquals(3, ma.sendMessage(SERVER, TO, CONTENT));
		verify(ms);	
	}
	
	
}
