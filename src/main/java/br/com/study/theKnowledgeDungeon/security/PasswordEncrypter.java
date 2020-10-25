package br.com.study.theKnowledgeDungeon.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncrypter {

	public static PasswordEncoder getEncrypter() {
		return new BCryptPasswordEncoder();
	}

}
