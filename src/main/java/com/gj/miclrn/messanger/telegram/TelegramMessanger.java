package com.gj.miclrn.messanger.telegram;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.gj.miclrn.http.HttpConstants;
import com.gj.miclrn.http.HttpService;
import com.gj.miclrn.messanger.MessageConstants;
import com.gj.miclrn.messanger.MessageService;

public class TelegramMessanger implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(TelegramMessanger.class);

    @Autowired
    HttpService httpService;
    @Value("${message.telegramToken}")
    private String token;

//	@Value("${message.chatId}")
//	private String chatId;

    @Override
    public void sendMessage(String Message) {
        sendMessage(Message, new HashMap<String, String>());
    }

    public Map<String, String> prepareHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:140.0) Gecko/20100101 Firefox/140.0");
        headers.put("Accept", "*/*");
        headers.put("Cache-Control", "no-cache");
        headers.put("Accept-Language", "en-US,en;q=0.5");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        return headers;
    }

    @Override
    public void sendMessage(String Message, Map<String, String> reqContext) {
        try {
            String chatId = reqContext.get("telegramChatId");
            String telegramToken = reqContext.get("telegramToken");
            Map<String, Object> context = new HashMap<>();
            context.put(HttpConstants.HEADERS, prepareHeaders());
            context.put(HttpConstants.ENTITY, Map.of("text", Message, "chat_id", chatId));
            httpService.sendPost(MessageConstants.TELEGRAM_URL + "/bot" + telegramToken != null ? telegramToken
                    : token + "/sendMessage", context);
        } catch (IOException e) {
            log.info("exception occured sending message {} Error->{}", Message, e);

        }
    }
}
