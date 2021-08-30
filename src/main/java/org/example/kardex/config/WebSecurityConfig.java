package org.example.kardex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(
				"/api/v1/Users/Login",
				"/api/v1/Customers/*",
				"/api/v1/Products/*",
				"/v2/api-docs",
				"/webjars/*",
				"/swagger-resources/*",
				"/swagger*"
			).permitAll()
			.anyRequest().authenticated();
	}
}