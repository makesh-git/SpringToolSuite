package com.securityWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors();
		http
		.csrf().disable()
		.authorizeRequests().antMatchers("/h2-console/**").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/postSaveRegUsers").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/getOneRegUsers/**").permitAll()
		.and()
		
		.authorizeRequests().antMatchers(HttpMethod.GET, "/login/**").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/downloadFile/**").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/isUserAvailable/**").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/isMotivatorAvailable/**").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/forgotPassword/**").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/changePassword/**").permitAll()
		.and()
		
		.authorizeRequests().antMatchers("/**").fullyAuthenticated()
		.anyRequest().authenticated()
		.and()
		.logout().invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login").permitAll();
		http.headers().frameOptions().disable();
		http.httpBasic();
	
		//super.configure(http);
	}



}













