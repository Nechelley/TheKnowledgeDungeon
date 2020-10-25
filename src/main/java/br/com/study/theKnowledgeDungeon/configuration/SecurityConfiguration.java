package br.com.study.theKnowledgeDungeon.configuration;

import br.com.study.theKnowledgeDungeon.enums.ProfileEnum;
import br.com.study.theKnowledgeDungeon.repository.UserRepository;
import br.com.study.theKnowledgeDungeon.security.AuthenticationByTokenFilter;
import br.com.study.theKnowledgeDungeon.security.PasswordEncrypter;
import br.com.study.theKnowledgeDungeon.service.onlyInterface.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	public static final String AUTHENTICATION_TYPE = "Bearer";
	public static final String AUTHORIZATION_HEADER_REQUEST = "Authorization";

	//login configurations
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(PasswordEncrypter.getEncrypter());
	}

	//autorization configurations
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/authentication").permitAll()

			.antMatchers(HttpMethod.GET, "/user").hasAuthority(ProfileEnum.ADMIN.getName())
			.antMatchers(HttpMethod.GET, "/user/*").hasAuthority(ProfileEnum.ADMIN.getName())
			.antMatchers(HttpMethod.POST, "/user/admin").hasAuthority(ProfileEnum.ADMIN.getName())
			.antMatchers(HttpMethod.POST, "/user").permitAll()
			.antMatchers(HttpMethod.DELETE, "/user").permitAll()

			//for h2
			.antMatchers("/").permitAll()
			//for h2
			.antMatchers("/h2-console/**").permitAll()

			.anyRequest().authenticated()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new AuthenticationByTokenFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class)
			//for h2
			.headers().frameOptions().disable();
	}

	//configuration of static resources like css and images
	@Override
	public void configure(WebSecurity web) { }

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
