package com.gj.miclrn.messanger.telegram.commandhandler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.entities.Identity;
import com.gj.miclrn.entities.IdentityNotificationPreferences;
import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.messanger.telegram.handler.TelegramCommandHandler;
import com.gj.miclrn.messangers.TelegramConstants;
import com.gj.miclrn.repositories.IdentityNotificationPreferencesRepository;
import com.gj.miclrn.repositories.IdentityRepository;

@Component
public class StartCommandHandler extends AbstractCommandHandler implements TelegramCommandHandler {

	@Autowired
	private IdentityRepository identityRepository;
	@Autowired
	private IdentityNotificationPreferencesRepository preferencesRepo;
	@Autowired
	@Qualifier("telegramMessanger")
	private MessageService messageService;

	@Override
	public boolean canHandle(String text) {
		return text.startsWith("/start");
	}

	@Override
	public void handle(JsonNode message) {
		JsonNode chat = message.get("chat");
		String chatId = chat.get("id").asText();
		boolean isUserAlreadyExists = preferencesRepo.existsByNUID(chatId);
		if (!isUserAlreadyExists) {
			IdentityNotificationPreferences preference = createNotificationPreference(chat);
			Identity newUser = createNewUser(chat, preference);
			identityRepository.save(newUser);
			messageService.sendMessage("Welcome to Microlearn Bot! You are registered to receive notifications.",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, preference.getNUID()));
		} else {
			messageService.sendMessage("Aleready Registerd", Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
		}
	}
}
