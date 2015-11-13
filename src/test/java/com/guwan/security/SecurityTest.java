package com.guwan.security;

import org.junit.Test;
import org.junit.runner.RunWith;
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

/**
 * @author EX-LUWENWU001
 *7u
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTest extends AbstractSecurityTests {

	  @Test
	  public void createUser(){
		  
		  	User user=new User();
			user.setUsername("user");
			user.setPassword(bCryptEncoder.encode("password"));
			ArrayList<Authority> a=new ArrayList<Authority>();
			a.add(new Authority(user,"ROLE_USER"));
			user.setAuthorities(a);
			repository.save(user);

			User reference = repository.findByUsername("user");
			
			assertNotNull(reference);
			assertEquals(user, reference);

			  
		  	User admin=new User();
		  	admin.setUsername("admin");
		  	admin.setPassword(bCryptEncoder.encode("password"));
			ArrayList<Authority> a2=new ArrayList<Authority>();
			a2.add(new Authority(admin,"ROLE_ADMIN"));
			a2.add(new Authority(admin,"ROLE_USER"));
			admin.setAuthorities(a2);
			repository.save(admin);

			User reference2 = repository.findByUsername("admin");
			
			assertNotNull(reference2);
			assertEquals(admin, reference2);
	  }
	/**
	 * test login
	 * @throws Exception 
	 */
	@Test
	public void testLogin() throws Exception{
		mvc
			.perform(formLogin("/sigin").user("username","admin").password("password","password"))
			.andExpect(authenticated().withRoles("USER", "ADMIN"));;
	}
//	/**
//	 * @throws Exception 
//	 * 
//	 */
//	@Test
//	public void testAdminUrl() throws Exception{
//		mvc
//	    .perform(get("/admin").with(user("user").roles("USER"))).andExpect(status().is4xxClientError());
//	}
//
//	/**
//	 * @throws Exception 
//	 * 
//	 */
//	@Test
//	public void testAdminUrlByRole_USER() throws Exception{
//		mvc
//	    .perform(get("/admin").with(user("admin").roles("ADMIN"))).andExpect(status().isOk());
//	}

}
