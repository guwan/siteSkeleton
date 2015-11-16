package com.guwan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.guwan.services.JpaUserDetailsManager;
import com.guwan.support.BCryptEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired JpaUserDetailsManager jpaUserDetailsManager;
	@Autowired BCryptEncoder bcryptEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .authorizeRequests()
	        .antMatchers("/signup","/about").permitAll() 
	        .antMatchers("/admin/**").hasRole("ADMIN") 
	        .anyRequest().authenticated()
	        .and()
	      .formLogin()
	      	.usernameParameter("username")
	      	.passwordParameter("password")
	        .loginPage("/login") 
	        .permitAll()
	       .and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jpaUserDetailsManager)
				.passwordEncoder(bcryptEncoder);
//			.inMemoryAuthentication()
//			.withUser("user").password("password").roles("USER").and()
//			.withUser("admin").password("password").roles("USER", "ADMIN");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/static/**","/resources/**","/dist/**","/img/**","/js/**","/css/**"); 
	}
}
