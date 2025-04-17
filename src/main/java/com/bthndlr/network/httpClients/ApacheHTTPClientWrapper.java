package com.bthndlr.network.httpClients;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class ApacheHTTPClientWrapper {
    private CloseableHttpClient client = HttpClients.createDefault();

    private CloseableHttpClient getcloseableClients() {

        return client;
    }

    public  CloseableHttpResponse get(String Uri) throws IOException {
            return client.execute(new HttpGet(Uri));
    }
    public CloseableHttpResponse post(String Uri) {
        return client.execute(new HttpPost(Uri));

    }

}
