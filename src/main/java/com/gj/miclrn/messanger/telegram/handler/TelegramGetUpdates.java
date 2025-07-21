package com.gj.miclrn.messanger.telegram.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.backgroundservices.ScheduledServies;
import com.gj.miclrn.backgroundservices.scheduledServices.ScheduleConfig;
import com.gj.miclrn.entities.ConfigSettings;
import com.gj.miclrn.entities.ConfigSettingsConstants;
import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.repositories.ConfigSettingsRepository;

@Component
@Scope("singleton")
public class TelegramGetUpdates implements ScheduledServies {

	private static final Logger log = LoggerFactory.getLogger(TelegramGetUpdates.class);
	private final ConfigSettingsRepository configSettingsRepository;
	private final MessageService messageService;
	private final TelegramCommandDispatcher telegramCommandDispatcher;

	@Autowired
	public TelegramGetUpdates(ConfigSettingsRepository configSettingsRepository,
			@Qualifier("telegramMessanger") MessageService messageService,
			TelegramCommandDispatcher telegramCommandDispatcher) {
		this.configSettingsRepository = configSettingsRepository;
		this.messageService = messageService;
		this.telegramCommandDispatcher = telegramCommandDispatcher;
	}
	@Override
	public boolean shouldSchedule() {
		return true;
	}
	@Override
	public ScheduleConfig getSchedule() {
		return new ScheduleConfig(20,TimeUnit.SECONDS); 
	}

	@Override
	public void run() {
		try {
			processTelegramUpdates();
		} catch (Exception e) {
			log.error("Error processing Telegram updates", e);
		}
	}

	private void processTelegramUpdates() {
		Optional<ConfigSettings> telegramOffset = configSettingsRepository
				.findByKey(ConfigSettingsConstants.TELEGRAM_OFFSET);// needs to implement redis to avoid db trasactions

		Map<String, Object> requestContext = createRequestContext(telegramOffset);
		JsonNode results = messageService.getUpdates(requestContext);

		if (results.get("ok").asBoolean()) {
			processSuccessfulResponse(results, requestContext, telegramOffset);
		} else {
			log.error("response not succeddfull {}", results);
		}

		log.info("Completed checking for updates");
	}

	private Map<String, Object> createRequestContext(Optional<ConfigSettings> telegramOffset) {
		Map<String, Object> context = new HashMap<>();
		String offsetValue = telegramOffset.map(ConfigSettings::getValue).orElse("0");
		context.put(ConfigSettingsConstants.TELEGRAM_OFFSET, offsetValue);
		return context;
	}

	private void processSuccessfulResponse(JsonNode results, Map<String, Object> requestContext,
			Optional<ConfigSettings> telegramOffset) {
		List<JsonNode> updates = convertResultsToList(results);
		AtomicLong MaxOffsetId = new AtomicLong(
				Long.parseLong((String) requestContext.get(ConfigSettingsConstants.TELEGRAM_OFFSET)));

		for (JsonNode update : updates) {
			processSingleUpdate(update, MaxOffsetId);
		}
		if (telegramOffset.isPresent() && updates.size() > 0) {
			MaxOffsetId.incrementAndGet();
			telegramOffset.get().setValue(MaxOffsetId.toString());
			configSettingsRepository.save(telegramOffset.get());
		}
	}

	private List<JsonNode> convertResultsToList(JsonNode results) {
		return StreamSupport.stream(results.get("result").spliterator(), false).collect(Collectors.toList());
	}

	private void processSingleUpdate(JsonNode update, AtomicLong MaxOffsetId) {
		try {
			Long updateId = update.get("update_id").asLong();
			JsonNode message = update.get("message");

			if (updateId > MaxOffsetId.get()) {
				MaxOffsetId.set(updateId);
			}
			telegramCommandDispatcher.dispatch(message);
		} catch (Exception e) {
			log.error("Exception processing {}", e);
		}

	}

}