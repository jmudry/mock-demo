package com.example.mockdemo;

public class MessageApp {

	private MessageService ms;
	
	public MessageApp(MessageService ms) {
		super();
		this.ms = ms;
	}

	public int sendMessage(String server, String to, String content) {
		if (ms.connect(server) == MessageStatus.CONNECTED) {
			switch (ms.sendMessage(to, content)) {
		        case SEND:  
		        		return 0;
				case INVALID_CONTENT:  
		        		return 3;
				case INVALID_RECEIPIENT:  
		        		return 4;
				default: 
		        		return 1;
			}
		} 
		return 2;			
	}
	
	

	
}
