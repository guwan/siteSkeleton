package com.guwan.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guwan.domain.Authority;
import com.guwan.domain.User;
import com.guwan.formBean.UserFormBean;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection; 

/**
 * @author EX-LUWENWU001
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityInteTest extends AbstractSecuritInteTests {

	  
	/**
	 * test login
	 * @throws Exception 
	 */
	@Test
	public void testLogin() throws Exception{  
		mvc
			.perform(formLogin("/login").user("username","admin").password("password","password"))
			.andExpect(authenticated().withRoles("USER", "ADMIN"));//.withUsername("admin"));
		
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

	@Test
	public void register() throws Exception {
		UserFormBean userFormBean = new UserFormBean();
		String timezone = UserFormBean.getTimezone(1941, 12, 16); 
		mvc.perform(
				post("/register")
					.param("username", "JoeSmith")
					.param("name", "Joe")
					.param("email", "joe@qq.com")
					.param("password", "password")
					.param("gender", "male")
					.param("birthDate", "1941-12-16")
					.param("phone", "+8613500000000"))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("pages/register"))
				.andExpect(flash().attribute("message",
								"Form submitted successfully.  Bound properties username=JoeSmith, "
										+"name=Joe, email=joe@qq.com, gender=male, "
										+ "birthDate=Tue Dec 16 08:00:00 " + timezone
										+ " 1941, phone=+8613500000000;"));
//		mockMvc
//				.perform(formLogin("/login").user("username","JoeSmith").password("password","password"))
//				.andExpect(authenticated().withRoles("USER"));
	}
//
//	/**
//	 * 因为还没有制作admin页面此处屏蔽。
//	 * @throws Exception 
//	 */
//	@Test
//	@WithMockUser(username="admin",roles={"USER","ADMIN"})
//	public void testAdminUrlByRole_USER() throws Exception{
////		mvc
////	    .perform(get("/admin")).andExpect(status().isOk());
//	}

}
