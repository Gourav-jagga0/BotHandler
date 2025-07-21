package com.gj.miclrn.messanger.telegram.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class TelegramCommandDispatcher {

	private final List<TelegramCommandHandler> handlers;

	@Autowired
	public TelegramCommandDispatcher(List<TelegramCommandHandler> handlers) {
		this.handlers = handlers;
	}

	public void dispatch(JsonNode message) {
		String text = message.get("text").asText();
		handlers.stream().filter(handler -> handler.canHandle(text)).findFirst()
				.ifPresent(handler -> handler.handle(message));
	}
}
