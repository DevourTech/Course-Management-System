package org.cms.client.framework.config;

public class UnInitializedConfigException extends Throwable {

	private final String msg;

	public UnInitializedConfigException(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "UnInitializedConfigException{" + "msg='" + msg + '\'' + '}';
	}
}
