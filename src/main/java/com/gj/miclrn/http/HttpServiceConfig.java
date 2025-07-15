package com.gj.miclrn.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gj.miclrn.apachehttp.ApacheHttpClientUtil;

@Configuration
public class HttpServiceConfig {

	@Value("${http.client.type:apache}")
	private String httpClientType;

	@Bean
	public HttpService httpService() {
		switch (httpClientType.toLowerCase()) {
		case "apache":
			return new ApacheHttpClientUtil(120);
		default:
			throw new RuntimeException("Unsupported HttpClient type: " + httpClientType);
		}
	}
}
