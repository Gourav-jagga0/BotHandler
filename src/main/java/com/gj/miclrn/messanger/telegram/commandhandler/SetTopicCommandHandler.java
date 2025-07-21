package com.gj.miclrn.messanger.telegram.commandhandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.entities.IdentityNotificationPreferences;
import com.gj.miclrn.entities.NotificationProperties;
import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.messanger.telegram.handler.TelegramCommandHandler;
import com.gj.miclrn.messangers.TelegramConstants;
import com.gj.miclrn.qrtz.TelegramSubscriptionScheduler;
import com.gj.miclrn.repositories.IdentityNotificationPreferencesRepository;

@Component
public class SetTopicCommandHandler extends AbstractCommandHandler implements TelegramCommandHandler {

	@Autowired
	private TelegramSubscriptionScheduler scheduler;
	@Autowired
	private IdentityNotificationPreferencesRepository preferencesRepo;
	@Autowired
	@Qualifier("telegramMessanger")
	private MessageService messageService;

	@Override
	public boolean canHandle(String text) {
		return text.startsWith("/settopic");
	}

	@Override
	public void handle(JsonNode message) {
		String chatId = message.get("chat").get("id").asText();
		String text = message.get("text").asText();
		String[] parts = text.split("\\|");
		if (parts.length > 3 || parts.length < 2) {
			messageService.sendMessage("Usage: /setTopic |content |cronExpression or /setTopic |content",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		String content = parts[1].trim();
		String schedule = (parts.length == 3) ? parts[2].trim() : "IMMEDIATE";

		Optional<IdentityNotificationPreferences> preferencesOpt = preferencesRepo.getByNUID(chatId);

		if (preferencesOpt.isEmpty()) {
			messageService.sendMessage("Please register first with /start",
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}
		IdentityNotificationPreferences preferences = preferencesOpt.get();
		boolean topicExists = preferences.getNotificationProperties().stream()
				.anyMatch(np -> np.getContent().equals(content));

		if (topicExists) {
			messageService.sendMessage("Topic already exists: " + content,
					Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
			return;
		}

		NotificationProperties newTopic = new NotificationProperties();
		newTopic.setContent(content);
		newTopic.setSchedule(schedule);

		if (preferences.getNotificationProperties() == null) {
			preferences.setNotificationProperties(new ArrayList<>());
		}

		preferences.getNotificationProperties().add(newTopic);
		preferencesRepo.save(preferences);
		try {
			scheduler.scheduleSubscription(chatId, content, schedule);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		messageService.sendMessage("Topic added successfully: " + content,
				Map.of(TelegramConstants.TELEGRAM_CHAT_ID, chatId));
	}
}
