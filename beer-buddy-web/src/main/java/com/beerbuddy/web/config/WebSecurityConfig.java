package com.beerbuddy.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

import com.beerbuddy.core.repository.UserRepository;
import com.beerbuddy.core.security.BeerBuddyAuthenticationManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public UserRepository userRepository;

	@Autowired
	Environment environment;
	
	@Bean
	public AuthenticationManager beerBuddyAuthenticationManager() {
		return new BeerBuddyAuthenticationManager(userRepository);
	}
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		//TODO: return BeerBuddyAuthenticationManager instead...
		return super.authenticationManager();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if( environment.getProperty("crsf.disable", Boolean.class, false) ) {
			log.debug("setting csrf to disabled...");
			http.csrf().disable();
		} else {
			final HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
			//see https://docs.angularjs.org/api/ng/service/$http
			tokenRepository.setHeaderName("X-XSRF-TOKEN");
			
			http.csrf().csrfTokenRepository(tokenRepository);
		}
		
		
	    //http://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/headers.html#headers-frame-options
	    http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN));
		
		http.authorizeRequests()
			.anyRequest()
				.anonymous()
			.anyRequest()
				.permitAll()
			;	
//		.fullyAuthenticated()
//			.and().formLogin()
//				.loginPage("/login").failureUrl("/login?error").permitAll();
	}
	
}
