package com.gj.miclrn.messanger;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public interface  MessageService {
    void sendMessage(String Message ,Map<String,Object>  reqContext);
	void sendMessage(String Message);
	JsonNode getUpdates(Map<String, Object> reqContext);
}
