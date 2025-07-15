package com.gj.miclrn.apachehttp;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.gj.miclrn.http.HttpConstants;
import com.gj.miclrn.http.HttpService;
import com.miclrn.utility.StringUtil;

@SuppressWarnings("unchecked")
public class ApacheHttpClientUtil  implements HttpService {

    private final CloseableHttpClient defaultHttpClient;

    public ApacheHttpClientUtil(int defaultTimeoutSeconds) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS)
                .setResponseTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS).build();

        this.defaultHttpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    private void beforeSend(HttpUriRequestBase req, Map<String, Object> context) {
        Map<String, String> headers = (Map<String, String>) context.getOrDefault(HttpConstants.HEADERS, Collections.emptyMap());
        headers.forEach(req::addHeader);
        String contentType = headers.get(HttpConstants.CONTENT_TYPE);
        if (StringUtil.isEmpty(contentType))
            return;
        BiConsumer<HttpUriRequestBase, Map<String, Object>> handler = EntityHandlers.entityHandlers.get(contentType);
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
        BasicCookieStore cookie = (BasicCookieStore) (context.get("cookie"));
        if (timeout != null) {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout, TimeUnit.SECONDS)
                    .setResponseTimeout(timeout, TimeUnit.SECONDS).build();
            return HttpClients.custom().setDefaultRequestConfig(config).setDefaultCookieStore(cookie).build();
        }
        return defaultHttpClient;
    }
    
}
