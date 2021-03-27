package org.cms.client.framework.session;

import org.cms.core.student.Student;

public class Session {

	int id;
	String passwd;
	public static final Session session = new Session();

	private Session() {}

	//singleton
	public static Session getInstance() {
		return session;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "Session{" + "id=" + id + ", passwd='" + passwd + '\'' + '}';
	}
}
