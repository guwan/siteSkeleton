package com.guwan.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ViewsControllerTests extends AbstractContextControllerTests {


	@Test
	public void htmlView() throws Exception {
		this.mockMvc.perform(get("/views/html"))
				.andExpect(view().name(containsString("views/html")))
				.andExpect(model().attribute("foo", "bar"))
				.andExpect(model().attribute("fruit", "apple"))
				.andExpect(model().size(2));
	}

	@Test
	public void viewName() throws Exception {
		this.mockMvc.perform(get("/views/viewName"))
				.andExpect(view().name(containsString("views/viewName")))
				.andExpect(model().attribute("foo", "bar"))
				.andExpect(model().attribute("fruit", "apple"))
				.andExpect(model().size(2));
	}

	@Test
	public void uriTemplate() throws Exception {
		this.mockMvc.perform(get("/views/pathVariables/bar/apple"))
				.andExpect(view().name(containsString("views/html")));
	}
}
