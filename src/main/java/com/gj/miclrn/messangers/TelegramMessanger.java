package com.gj.miclrn.messangers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.entities.ConfigSettingsConstants;
import com.gj.miclrn.http.HttpConstants;
import com.gj.miclrn.http.HttpService;
import com.gj.miclrn.messanger.MessageConstants;
import com.gj.miclrn.messanger.MessageService;
import com.gj.miclrn.utility.Utility;

@Component("telegramMessanger")
public class TelegramMessanger implements MessageService {

	private static final Logger log = LoggerFactory.getLogger(TelegramMessanger.class);

	@Autowired
	private HttpService httpService;
//	@Autowired
//	private TelegramGetUpdates tlgUpdates;

	@Value("${telegram.telegramToken:null}")
	private String token;

//	@Override
//	public Set<Runnable> getScheduleServices() {
//		return Set.of(tlgUpdates);
//	}

	@Override
	public void sendMessage(String message) {
		sendMessage(message, new HashMap<>());
	}

	@Override
	public void sendMessage(String message, Map<String, Object> reqContext) {
		String chatId = (String) reqContext.get(TelegramConstants.TELEGRAM_CHAT_ID);
		String telegramToken = getTelegramToken(reqContext);

		Map<String, Object> entity = Map.of(TelegramConstants.TELEGRAM_MESSAGE, message,
				TelegramConstants.TELEGRAM_CHAT_ID, chatId);

		Map<String, Object> context = new HashMap<>();
		context.put(HttpConstants.HEADERS, prepareHeaders(reqContext));
		context.put(HttpConstants.ENTITY, entity);
		try {
			httpService.sendPost(getUrl(telegramToken, TelegramConstants.TELEGRAM_SEND_MESSAGE), context);
		} catch (IOException e) {
			log.error("Error sending Telegram message: {}", message, e);
		}
	}

	@Override
	public JsonNode getUpdates(Map<String, Object> reqContext) {
		String telegramToken = getTelegramToken(reqContext);
		Object offset = reqContext.get(ConfigSettingsConstants.TELEGRAM_OFFSET);

		Map<String, Object> entity = Map.of(TelegramConstants.TELEGRAM_OFFSET, offset);

		Map<String, Object> context = new HashMap<>();
		context.put(HttpConstants.HEADERS, prepareHeaders(reqContext));
		context.put(HttpConstants.ENTITY, entity);
		try {
			String response = httpService.sendPost(getUrl(telegramToken, TelegramConstants.TELEGRAM_GET_UPDATES),
					context);
			return Utility.getJsonNodeFromString(response);
		} catch (IOException e) {
			log.error("Error retrieving Telegram updates", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> prepareHeaders(Map<String, Object> reqContext) {
		if (reqContext.containsKey(HttpConstants.REQ_HEADERS)) {
			return (Map<String, String>) reqContext.get(HttpConstants.REQ_HEADERS);
		}
		return httpService.getDefaultHeader();
	}

	private String getUrl(String token, String endPoint) {
		return MessageConstants.TELEGRAM_URL + TelegramConstants.TELEGRAM_BOT + token + endPoint;
	}

	private String getTelegramToken(Map<String, Object> reqContext) {
		String telegramToken = (String) reqContext.get(TelegramConstants.TELEGRAM_TOKEN);
		return (telegramToken != null && !telegramToken.isEmpty()) ? telegramToken : token;
	}
}
