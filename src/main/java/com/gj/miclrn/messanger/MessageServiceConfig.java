package com.gj.miclrn.messanger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gj.miclrn.messanger.telegram.TelegramMessanger;

@Configuration
public class MessageServiceConfig {

	@Value("${message.services}")
	private List<String> messageServices;

	@Bean
	public MessageService messageService() {
		return new TelegramMessanger();
	}

}
