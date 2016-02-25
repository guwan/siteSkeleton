package com.guwan.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.creation.util.MockitoMethodProxy;
import org.mockito.internal.invocation.MockitoMethod;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.guwan.domain.User;
import com.guwan.formBean.UserFormBean;
import com.guwan.services.JpaUserDetailsManager;



public class RegisterControllerTests {

	private MockMvc mockMvc;
	@Mock
    private JpaUserDetailsManager userDetailsManager;
	@Before
	public void setup() throws Exception {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");

		this.mockMvc = standaloneSetup(new RegisterController(userDetailsManager)).setViewResolvers(viewResolver).build();
		Mockito.doNothing().when(userDetailsManager);
		
	}

	@Test
	public void submitSuccess() throws Exception {
		String timezone = getTimezone(1941, 12, 16); 
		this.mockMvc.perform(
				post("/register")
					.param("username", "Joe Smith")
					.param("name", "Joe")
					.param("email", "joe@qq.com")
					.param("password", "password")
					.param("gender", "male")
					.param("birthDate", "1941-12-16")
					.param("phone", "+8613500000000"))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/register"))
				.andExpect(flash().attribute("message",
								"Form submitted successfully.  Bound properties username=Joe Smith, "
										+"name=Joe, email=joe@qq.com, gender=male, "
										+ "birthDate=Tue Dec 16 08:00:00 " + timezone
										+ " 1941, phone=+8613500000000;"));
	}

	@Test
	public void submitSuccessAjax() throws Exception {
		String timezone = getTimezone(1941, 12, 16); 
		this.mockMvc.perform(
				post("/register")
					.header("X-Requested-With", "XMLHttpRequest")
					.param("username", "Joe Smith")
					.param("name", "Joe")
					.param("email", "joe@qq.com")
					.param("password", "password")
					.param("gender", "male")
					.param("birthDate", "1941-12-16")
					.param("phone", "+8613500000000"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("message",
								"Form submitted successfully.  Bound properties username=Joe Smith, "
										+"name=Joe, email=joe@qq.com, gender=male, "
										+ "birthDate=Tue Dec 16 08:00:00 " + timezone
										+ " 1941, phone=+8613500000000;"));
	}

	@Test
	public void submitError() throws Exception {
		this.mockMvc.perform(
				post("/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().errorCount(4))
				.andExpect(model()
						.attributeHasFieldErrors("userFormBean", "username",
								"name", "email", "password"));
	}
	
	private String getTimezone(int year, int month, int day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		Date date = calendar.getTime();
		TimeZone timezone = TimeZone.getDefault();
		boolean inDaylight = timezone.inDaylightTime(date);
		return timezone.getDisplayName(inDaylight, TimeZone.SHORT);
	}

}
