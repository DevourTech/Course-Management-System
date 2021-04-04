package org.cms.client.framework.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {

	private final int rounds;

	public PasswordEncoder(int rounds) {
		this.rounds = rounds;
	}

	public String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(rounds);

		return BCrypt.hashpw(password_plaintext, salt);
	}
}
