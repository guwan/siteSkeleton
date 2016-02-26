package com.guwan.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guwan.formBean.UserFormBean;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerInteTests extends ContextControllerIntegrationTests {


	@Test
	public void register() throws Exception {
		UserFormBean userFormBean = new UserFormBean();
		mockMvc.perform(get("/register"))
				//.andDo(print())
				.andExpect(view().name(containsString("register")))
				.andExpect(model().attribute("ajaxRequest", false))
				.andExpect(model().attribute("userFormBean", userFormBean))
				.andExpect(model().size(2));
	}

}
