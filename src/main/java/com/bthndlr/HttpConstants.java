package com.bthndlr;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public interface HttpConstants {
    String CONTENT_TYPE = "Content-Type";
    String HEADERS = "HEADERS";
    String ENTITY = "ENTITY";
    enum ContentType {
        FORM("application/x-www-form-urlencoded"), JSON("application/json");

        private final String value;

        ContentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        // Get a Set of all content type values
        public static Set<String> getValues() {
            return Arrays.stream(values()).map(ContentType::getValue).collect(Collectors.toSet());
        }
    }
}
