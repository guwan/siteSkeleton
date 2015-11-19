package com.guwan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.guwan.security.CustomAuthenticationProvider;
import com.guwan.security.CustomVoter;
import com.guwan.services.JpaUserDetailsManager;
import com.guwan.support.BCryptEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired JpaUserDetailsManager jpaUserDetailsManager;
	@Autowired BCryptEncoder bcryptEncoder;
	@Autowired CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired CustomVoter customVoter;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
	      .authorizeRequests().accessDecisionManager(accessDecisionManager());
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
	
	@Bean(name = "accessDecisionManager")
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		decisionVoters.add(customVoter);
        decisionVoters.add(new RoleVoter());  
        decisionVoters.add(new AuthenticatedVoter());  

		AffirmativeBased accessDecisionManager = new AffirmativeBased(decisionVoters);

		return accessDecisionManager;
	}
	/*
	 * 表达式控制器
	 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		return webSecurityExpressionHandler;
	}

	/*
	 * 表达式投票器
	 */
	@Bean(name = "expressionVoter")
	public WebExpressionVoter webExpressionVoter() {
		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
		return webExpressionVoter;
	}
	  
}
