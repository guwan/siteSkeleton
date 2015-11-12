package com.guwan.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guwan.domain.User;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view; 

@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTest extends AbstractSecurityTests {
	
	/**
	 * test login
	 * @throws Exception 
	 */
	@Test
	public void testLogin() throws Exception{
		mvc
			.perform(formLogin("/login").user("username","admin").password("password","password"))
			.andExpect(authenticated().withRoles("USER", "ADMIN"));;
	}
	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testAdminUrl() throws Exception{
		mvc
	    .perform(get("/admin").with(user("user").roles("USER"))).andExpect(status().is4xxClientError());
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testAdminUrlByRole_USER() throws Exception{
		mvc
	    .perform(get("/admin").with(user("admin").roles("ADMIN"))).andExpect(status().isOk());
	}

}
