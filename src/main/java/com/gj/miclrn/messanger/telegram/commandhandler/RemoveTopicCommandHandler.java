package com.gj.miclrn.messanger.telegram.commandhandler;

import java.util.Map;
import java.util.Optional;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.entities.IdentityNotificationPreferences;
import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.messanger.telegram.handler.TelegramCommandHandler;
import com.gj.miclrn.messangers.TelegramConstants;
import com.gj.miclrn.qrtz.TelegramSubscriptionScheduler;
import com.gj.miclrn.repositories.IdentityNotificationPreferencesRepository;
import com.gj.miclrn.repositories.IdentityRepository;

@Component
public class RemoveTopicCommandHandler extends AbstractCommandHandler implements TelegramCommandHandler {
	@Autowired
	private TelegramSubscriptionScheduler scheduler;
	@Autowired
	private IdentityNotificationPreferencesRepository preferencesRepo;
	@Autowired
	@Qualifier("telegramMessanger")
	private MessageService messageService;

	@Override
	public boolean canHandle(String text) {
		return text.startsWith("/removetopic");
	}

	@Override
	public void handle(JsonNode message) {
		String chatId = message.get("chat").get("id").asText();
		String text = message.get("text").asText();

		String[] parts = text.split("\\|");
		if (parts.length != 2) {
			messageService.sendMessage("Usage: /removeTopic |content",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		String content = parts[1];

		Optional<IdentityNotificationPreferences> preferencesOpt = preferencesRepo.getByNUID(chatId);

		if (preferencesOpt.isEmpty()) {
			messageService.sendMessage("Please register first with /start",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		IdentityNotificationPreferences preferences = preferencesOpt.get();

		if (preferences.getNotificationProperties() == null) {
			messageService.sendMessage("You have no topics set up.",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		boolean removed = preferences.getNotificationProperties().removeIf(np -> np.getContent().equals(content));

		if (removed) {
			preferencesRepo.save(preferences);
			messageService.sendMessage("Topic removed: " + content, Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			try {
				scheduler.unscheduleSubscription(chatId, content);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			messageService.sendMessage("Topic not found: " + content,
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
		}
	}
}
