package com.gj.miclrn.http;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHttpServiceImpl implements HttpService {
	public Map<String, String> getDefaultHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:140.0) Gecko/20100101 Firefox/140.0");
		headers.put("Accept", "*/*");
		headers.put("Cache-Control", "no-cache");
		headers.put("Accept-Language", "en-US,en;q=0.5");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		return headers;
	}
}
