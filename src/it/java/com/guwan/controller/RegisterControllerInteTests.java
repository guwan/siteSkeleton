package com.guwan.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guwan.formBean.UserFormBean;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerInteTests extends ContextControllerIntegrationTests {


	@Test
	public void registerPage() throws Exception {
		UserFormBean userFormBean = new UserFormBean();
		mockMvc.perform(get("/register"))
				//.andDo(print())
				.andExpect(view().name(containsString("register")))
				.andExpect(model().attribute("ajaxRequest", false))
				.andExpect(model().attribute("userFormBean", userFormBean))
				.andExpect(model().size(2));
	}
	@Test
	public void register() throws Exception {
		UserFormBean userFormBean = new UserFormBean();
		String timezone = UserFormBean.getTimezone(1941, 12, 16); 
		this.mockMvc.perform(
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
		mockMvc
				.perform(formLogin("/login").user("username","JoeSmith").password("password","password"))
				.andExpect(authenticated().withRoles("USER"));
	}

}
