package com.bthndlr.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.core.layout.StringBuilderEncoder;
import org.apache.logging.log4j.core.util.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import com.bthndlr.HttpConstants;
import com.bthndlr.HttpService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.StringEscapeUtils;

public class GPTextension implements IDataGenerator {
    @Autowired
    HttpService serv;
    private static final String API_KEY = "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";
    private static final String URL = "http://localhost:11434/api/generate";

    @Override
    public String getStrData() {
        try {
             String data=serv.sendPost(URL, prepareContext());
            JsonNode JsonData= new ObjectMapper().readTree(data);
          
             return  HtmlUtils.htmlEscape(JsonData.get("response").asText()) ;
        } catch (IOException e) {

        }
        return null;
    }

    private Map<String, Object> prepareContext() {
        Map<String, Object> context = new HashMap<>();
        context.put(HttpConstants.HEADERS, Map.of(
                // "Authorization", "Bearer " + API_KEY,
                "Content-Type", "application/json"));
        context.put(HttpConstants.ENTITY, """
                  {
                  "model": "mistral",
                  "prompt": "Write a Java function to check if a number is prime.",
                  "stream": false
                }
                """);
        return context;
    }

}
