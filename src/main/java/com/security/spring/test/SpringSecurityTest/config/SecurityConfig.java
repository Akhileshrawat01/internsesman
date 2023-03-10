package com.security.spring.test.SpringSecurityTest.config;


//import static org.springframework.security.config.Customizer.withDefaults;

//import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;




//import com.security.spring.test.SpringSecurityTest.security.JwtAuthenticationFilter;
@Configuration
public class SecurityConfig {
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}

	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails normalUser=User
				.withUsername("akhil")
				.password(passwordEncoder().encode("pass"))
				.roles("NORMAL")
				.build();
		
		return new InMemoryUserDetailsManager(normalUser);
				
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception  {
		
		httpSecurity.csrf().disable()
			.sessionManagement(session -> session
	            .maximumSessions(1)
	        )
			
			.sessionManagement(session -> session
		        .invalidSessionUrl("/home/login")
		     )
			.authorizeHttpRequests()
		
			.requestMatchers("/home/login")
			.hasRole("NORMAL")
			
			
			.anyRequest()
			.denyAll()
			.and()
			.formLogin();

		return httpSecurity.build();

	}
	
	
	
	
}

