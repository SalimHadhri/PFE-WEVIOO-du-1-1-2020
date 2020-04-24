package com.wsourcing.Eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

	@EnableWebSecurity
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		/**
		 * disable CSRF
		 * @param http
		 * @throws Exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.csrf()
					.disable()
					.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.httpBasic();
		}
	}


}
