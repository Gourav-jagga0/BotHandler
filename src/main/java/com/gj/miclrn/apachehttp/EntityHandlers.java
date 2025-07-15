package com.gj.miclrn.apachehttp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.gj.miclrn.http.HttpConstants;

public class EntityHandlers {
	public static final Map<String, BiConsumer<HttpUriRequestBase, Map<String, Object>>> entityHandlers = Map.of(
			HttpConstants.ContentType.FORM.getValue(), EntityHandlers::handleFormEntity,
			HttpConstants.ContentType.JSON.getValue(), EntityHandlers::handleJsonEntity);;

	private static void handleFormEntity(HttpUriRequestBase req, Map<String, Object> context) {
		Map<String, String> formData = (Map<String, String>) context.getOrDefault(HttpConstants.ENTITY,
				Collections.emptyMap());
		List<NameValuePair> params = new ArrayList<>();
		formData.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
		req.setEntity(new UrlEncodedFormEntity(params));
	}

	private static void handleJsonEntity(HttpUriRequestBase req, Map<String, Object> context) {
		String entity = (String) context.get(HttpConstants.ENTITY);
		if (entity != null) {
			req.setEntity(new StringEntity(entity, ContentType.APPLICATION_JSON));
		}
	}

}
