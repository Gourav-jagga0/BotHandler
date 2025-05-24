package com.bthndlr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.bthndlr.utility.StringUtil;

@SuppressWarnings("unchecked")
public class ApacheHttpClientUtil  implements HttpService {

    private final CloseableHttpClient defaultHttpClient;
    private final Map<String, BiConsumer<HttpUriRequestBase, Map<String, Object>>> entityHandlers;

    public ApacheHttpClientUtil(int defaultTimeoutSeconds) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS)
                .setResponseTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS).build();

        this.defaultHttpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        this.entityHandlers = Map.of(HttpConstants.ContentType.FORM.getValue(), this::handleFormEntity,
                HttpConstants.ContentType.JSON.getValue(), this::handleJsonEntity);
    }

    private void beforeSend(HttpUriRequestBase req, Map<String, Object> context) {
        Map<String, String> headers = (Map<String, String>) context.getOrDefault(HttpConstants.HEADERS, Collections.emptyMap());
        headers.forEach(req::addHeader);
        String contentType = headers.get(HttpConstants.CONTENT_TYPE);
        if (StringUtil.isEmpty(contentType))
            return;
        BiConsumer<HttpUriRequestBase, Map<String, Object>> handler = entityHandlers.get(contentType);
        if (handler == null) {
            throw new UnsupportedOperationException("Content Type not supported: " + contentType);
        }
        handler.accept(req, context);
    }

    public String sendGet(String url, Map<String, Object> context) throws IOException {
        HttpGet request = new HttpGet(url);
        beforeSend(request, context);
        CloseableHttpClient client = getHttpClientFromContext(context);
        return client.execute(request, response -> EntityUtils.toString(response.getEntity()));
    }

    public String sendPost(String url, Map<String, Object> context) throws IOException {
        HttpPost request = new HttpPost(url);
        beforeSend(request, context);
        CloseableHttpClient client = getHttpClientFromContext(context);
        return client.execute(request, response -> EntityUtils.toString(response.getEntity()));
    }

    private CloseableHttpClient getHttpClientFromContext(Map<String, Object> context) {
        Integer timeout = (Integer) context.get("timeout");
        if (timeout != null) {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout, TimeUnit.SECONDS)
                    .setResponseTimeout(timeout, TimeUnit.SECONDS).build();
            return HttpClients.custom().setDefaultRequestConfig(config).build();
        }
        return defaultHttpClient;
    }

    private void handleFormEntity(HttpUriRequestBase req, Map<String, Object> context) {
        Map<String, String> formData = (Map<String, String>) context.getOrDefault(HttpConstants.ENTITY, Collections.emptyMap());
        List<NameValuePair> params = new ArrayList<>();
        formData.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
        req.setEntity(new UrlEncodedFormEntity(params));
    }

    private void handleJsonEntity(HttpUriRequestBase req, Map<String, Object> context) {
        String entity = (String) context.get(HttpConstants.ENTITY);
        if (entity != null) {
            req.setEntity(new StringEntity(entity,ContentType.APPLICATION_JSON));
        }
    }
}
