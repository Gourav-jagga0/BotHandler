package com.gj.miclrn.messanger.telegram.commandhandler;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.entities.IdentityNotificationPreferences;
import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.messanger.telegram.handler.TelegramCommandHandler;
import com.gj.miclrn.messangers.TelegramConstants;
import com.gj.miclrn.repositories.IdentityNotificationPreferencesRepository;
import com.gj.miclrn.repositories.IdentityRepository;

@Component
public class StopCommandHandler extends AbstractCommandHandler implements TelegramCommandHandler {

	@Autowired
	private IdentityNotificationPreferencesRepository preferencesRepo;
	@Autowired
	@Qualifier("telegramMessanger")
	private MessageService messageService;

	@Override
	public boolean canHandle(String text) {
		return text.startsWith("/stop");
	}

	@Override
	public void handle(JsonNode message) {
		JsonNode chat = message.get("chat");
		String chatId = chat.get("id").asText();
		Optional<IdentityNotificationPreferences> identityNotificationPreferences = preferencesRepo.getByNUID(chatId);
		if (identityNotificationPreferences.isPresent()) {
			identityNotificationPreferences.get().setInt_status(9);
			preferencesRepo.save(identityNotificationPreferences.get());
			messageService.sendMessage("Welcome to Microlearn Bot! You are registered to receive notifications.",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
		} else {
			messageService.sendMessage("Aleready deregistered", Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
		}
	}
}
