package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
				.requestMatchers(HttpMethod.POST, "/user/**").hasRole("USER")
				.requestMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().permitAll()
			.and()
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?failure")
				.defaultSuccessUrl("/userComment")
			.and()
				.logout()
				.logoutSuccessUrl("/login")
				.permitAll();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 非推奨: セキュリティ上のリスクがあるため、開発以外では使用しない
		return NoOpPasswordEncoder.getInstance();
	}

}
