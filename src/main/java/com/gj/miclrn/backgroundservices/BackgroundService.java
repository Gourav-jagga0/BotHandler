package com.gj.miclrn.backgroundservices;

import org.springframework.context.ApplicationContext;

public interface BackgroundService {
	void init(ApplicationContext appCntxr);
}
