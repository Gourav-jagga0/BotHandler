package com.gj.miclrn.messanger.telegram.commandhandler;

import java.util.ArrayList;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.gj.miclrn.entities.Identity;
import com.gj.miclrn.entities.IdentityNotificationPreferences;
import com.gj.miclrn.entityBuilder.LambdaEntityBuilder;
import com.gj.miclrn.messanger.telegram.handler.TelegramCommandHandler;

public abstract class AbstractCommandHandler implements TelegramCommandHandler {
	protected IdentityNotificationPreferences createNotificationPreference(JsonNode chat) {
		IdentityNotificationPreferences preference = LambdaEntityBuilder.of(IdentityNotificationPreferences::new)
				.with(IdentityNotificationPreferences::setNUID, chat.get("id").asText()).build();
		preference.setNotificationProperties(new ArrayList<>());
		return preference;
	}

	protected Identity createNewUser(JsonNode chat, IdentityNotificationPreferences preference) {
		return LambdaEntityBuilder.of(Identity::new).with(Identity::setFirstName, chat.get("first_name").asText())
				.with(Identity::setLastName, chat.get("last_name").asText())
				.with(Identity::setNotificationPreferences, Set.of(preference)).build();
	}

}
