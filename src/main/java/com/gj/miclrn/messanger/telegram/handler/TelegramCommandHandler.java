package com.gj.miclrn.messanger.telegram.handler;

import com.fasterxml.jackson.databind.JsonNode;

public interface TelegramCommandHandler {
    boolean canHandle(String text);
    void handle(JsonNode message);
}

