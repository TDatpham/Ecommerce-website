package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppSecurityConfig implements WebMvcConfigurer {
	

	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/app/**")
	            .allowedOriginPatterns("http://127.0.0.1:5500",  "https://zesty-mousse-f100c7.netlify.app/") // Explicitly specify allowed origins
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	            .allowCredentials(true)
	            .allowedHeaders("*")
	            .exposedHeaders(HttpHeaders.AUTHORIZATION);
	}

	
	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http)throws Exception{
		
		return http.csrf(customizer -> customizer.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(
				request -> request.requestMatchers("/app/customer/register", 
				"/app/admin/register",
				"/app/search/**", 
				"/app/category/**",
				"/app/reviews/**",
				"/app/forgot-password", 
				"/app/reset-password").permitAll()
				.requestMatchers("/app/customer/**").hasAuthority("CUSTOMER")
				.requestMatchers("/app/admin/**").hasAuthority("ADMIN").anyRequest().authenticated()
				)
			.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
        	.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
        	.formLogin(customizer -> customizer.disable())
        	.httpBasic(Customizer.withDefaults())
        	.build();
			
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

}
