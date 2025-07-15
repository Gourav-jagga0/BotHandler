package com.miclrn.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class Constants {

	@Value("${naukri.portalurl:https://www.naukri.com/jobapi/v3/search"
			+ "?noOfResults=100&urlType=search_by_keyword&searchType=adv&"
			+ "keyword=java%2C%20java%20developer%2C%20java%20development%2C%20core%20java%20development%2C%20java%20programming%2C%20core%20java%20programming%2C%20java%20coding%2C%20j2ee%20development%2C%20java%20standard%20edition%2C%20java%20collections"
			+ "&pageNo=1&experience=4&jobAge=1&"
			+ "k=java%2C%20java%20developer%2C%20java%20development%2C%20core%20java%20development%2C%20java%20programming%2C%20core%20java%20programming%2C%20java%20coding%2C%20j2ee%20development%2C%20java%20standard%20edition%2C%20java%20collections"
			+ "&nignbevent_src=jobsearchDeskGNB" + "&experience=4&jobAge=1"
			+ "&seoKey=java-developer-java-development-core-java-development-java-programming-core-java-programming-java-coding-j2ee-development-java-standard-edition-java-collections-jobs"
			+ "&src=directSearch&latLong=29.9602053_74.7012803}")
	public String jobPortal;
	@Value("${naukri.username}")
	public String username;
	@Value("${naukri.password}")
	public String password;
	@Value("${naukri.loginurl}")
	public String loginurl;
	public String domain="https://www.naukri.com";

}
