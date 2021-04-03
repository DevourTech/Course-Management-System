package org.cms.client.framework.rest;

import org.cms.client.framework.config.Config;

import java.net.http.HttpClient;

public class RestClientImpl {
	private HttpClient httpClient;
	private String host;

	public RestClientImpl() {
		httpClient = HttpClient.newBuilder().build();
		host = Config.get(Config.CMS_HOST) + Config.get(Config.CMS_API_PATH);
	}


}
