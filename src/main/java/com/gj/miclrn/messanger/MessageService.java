package com.gj.miclrn.messanger;

import java.util.Map;

public interface  MessageService {
    void sendMessage(String Message ,Map<String,String>  reqContext);
	void sendMessage(String Message);
}
