package com.example.mockdemo;

public interface MessageService {

	MessageStatus connect(String server);
	
	MessageStatus sendMessage(String to, String content);
		
}
