package br.com.study.theKnowledgeDungeon.service.onlyInterface;

import org.springframework.security.core.Authentication;

public interface TokenService {

	String createToken(Authentication authentication);

	boolean isValidToken(String token);

	Long getUserId(String token);

}
