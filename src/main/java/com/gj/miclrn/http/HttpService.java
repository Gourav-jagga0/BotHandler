package com.gj.miclrn.http;

import java.io.IOException;
import java.util.Map;

public interface HttpService {
	public String sendPost(String url, Map<String, Object> context) throws IOException;

	public String sendGet(String url, Map<String, Object> context) throws IOException;

}
