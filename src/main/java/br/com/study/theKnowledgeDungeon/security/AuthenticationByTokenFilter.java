package br.com.study.theKnowledgeDungeon.security;

import br.com.study.theKnowledgeDungeon.configuration.SecurityConfiguration;
import br.com.study.theKnowledgeDungeon.model.User;
import br.com.study.theKnowledgeDungeon.repository.UserRepository;
import br.com.study.theKnowledgeDungeon.service.onlyInterface.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.study.theKnowledgeDungeon.configuration.SecurityConfiguration.AUTHORIZATION_HEADER_REQUEST;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository userRepository;

	public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = recoverToken(request);
		boolean isValid = tokenService.isValidToken(token);
		if (isValid)
			authenticateUser(token);

		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION_HEADER_REQUEST);

		if (token == null || token.isEmpty() || !token.startsWith(SecurityConfiguration.AUTHENTICATION_TYPE + " "))
			return null;

		return token.substring(7);
	}

	private void authenticateUser(String token) {
		Long userId = tokenService.getUserId(token);
		User user = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
