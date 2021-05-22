package org.cms.client.framework.session;

import org.cms.client.framework.rest.RestClient;
import org.cms.client.framework.rest.RestClientImpl;

public class Session {

	private final SessionConfig sessionConfig;
	private final RestClient restClient;

	public Session(SessionConfig sessionConfig) {
		this.sessionConfig = sessionConfig;
		this.restClient =
			new RestClientImpl(
				sessionConfig.getHostname(),
				sessionConfig.getUserId(),
				sessionConfig.getPassword(),
				sessionConfig.getUserType()
			);
	}

	public RestClient getRestClient() {
		return restClient;
	}

	public void authenticate() throws Exception {
		boolean authStatus = restClient.authenticate();
		if (!authStatus) {
			throw new FailedAuthenticationException();
		}
	}

	@Override
	public String toString() {
		return "Session{" + "sessionConfig=" + sessionConfig + '}';
	}
}
