package com.example.mockdemo;

import org.junit.Before;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class EasyMockTest {

	
	private MessageApp ma;
	private MessageService ms;
	
	@Before
	public void setUP(){
		ms = createMock(MessageService.class);
		ma = new MessageApp(ms);
	}
	
	@Test
	public void sendingOkCheck() {
		expect(ms.connect("server")).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage("to", "content")).andReturn(MessageStatus.SEND);
		replay(ms);
		
		assertEquals(0, ma.sendMessage("server", "to", "content"));
		verify(ms);	
	}
	
	@Test
	public void sendingNotConnectedCheck() {
		expect(ms.connect("server")).andReturn(MessageStatus.CONNECTION_ERROR);
		replay(ms);
		
		assertEquals(2, ma.sendMessage("server", "to", "content"));
		verify(ms);
	}
	
	@Test
	public void sendingErrorCheck() {
		expect(ms.connect("server")).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage("to", "content")).andReturn(MessageStatus.SENDING_ERROR);
		replay(ms);
		
		assertEquals(1, ma.sendMessage("server", "to", "content"));
		verify(ms);	
	}
	
	@Test
	public void sendingErrorReceipientCheck() {
		expect(ms.connect("server")).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage("to", "content")).andReturn(MessageStatus.INVALID_RECEIPIENT);
		replay(ms);
		
		assertEquals(4, ma.sendMessage("server", "to", "content"));
		verify(ms);	
	}
	
	@Test
	public void sendingErrorContentCheck() {
		expect(ms.connect("server")).andReturn(MessageStatus.CONNECTED);
		expect(ms.sendMessage("to", "content")).andReturn(MessageStatus.INVALID_CONTENT);
		replay(ms);
		
		assertEquals(3, ma.sendMessage("server", "to", "content"));
		verify(ms);	
	}
	
	
}
