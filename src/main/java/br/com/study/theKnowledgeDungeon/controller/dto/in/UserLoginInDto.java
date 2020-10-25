package br.com.study.theKnowledgeDungeon.controller.dto.in;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class UserLoginInDto {

	private String email;
	private String password;

	public UsernamePasswordAuthenticationToken createAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
