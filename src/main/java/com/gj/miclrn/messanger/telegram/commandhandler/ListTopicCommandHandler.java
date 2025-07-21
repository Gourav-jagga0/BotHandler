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

@Component
public class ListTopicCommandHandler implements TelegramCommandHandler {

	@Autowired
	private IdentityNotificationPreferencesRepository preferencesRepo;
	@Autowired
	@Qualifier("telegramMessanger")
	private MessageService messageService;

	@Override
	public boolean canHandle(String text) {
		return text.startsWith("/listtopic");
	}

	@Override
	public void handle(JsonNode message) {
		String chatId = message.get("chat").get("id").asText();

		Optional<IdentityNotificationPreferences> preferencesOpt = preferencesRepo.getByNUID(chatId);

		if (preferencesOpt.isEmpty()) {
			messageService.sendMessage("Please register first with /start",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		IdentityNotificationPreferences preferences = preferencesOpt.get();

		if (preferences.getNotificationProperties() == null || preferences.getNotificationProperties().isEmpty()) {
			messageService.sendMessage("You have no topics set up.",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		StringBuilder response = new StringBuilder("Your topics:\n");
		preferences.getNotificationProperties().forEach(np -> {
			response.append("- ").append(np.getContent()).append(" (schedule: ").append(np.getSchedule()).append(")\n");
		});

		messageService.sendMessage(response.toString(), Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
	}
}
