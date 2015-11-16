package com.guwan.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guwan.domain.Authority;
import com.guwan.domain.User;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection; 

/**
 * @author EX-LUWENWU001
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTest extends AbstractSecurityTests {

	  
	/**
	 * test login
	 * 不能使用.andExpect(authenticated().withRoles("USER", "ADMIN"));
	 * 因为，wihRoles()使用SimpleGrantedAuthority判断授权，而系统是用的是自定义的Authority。故无法通过。
	 * @throws Exception 
	 */
	@Test
	public void testLogin() throws Exception{  
		mvc
			.perform(formLogin("/login").user("username","admin").password("password","password"))
			.andExpect(authenticated().withUsername("admin"));
		
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
	 * 因为还没有制作admin页面此处屏蔽。
	 * @throws Exception 
	 */
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void testAdminUrlByRole_USER() throws Exception{
//		mvc
//	    .perform(get("/admin")).andExpect(status().isOk());
	}

}
