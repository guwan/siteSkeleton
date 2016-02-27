package com.guwan.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.guwan.Application;
import com.guwan.BeanConfig;
import com.guwan.CustomRepositoryConfig;

@ContextConfiguration(classes ={ CustomRepositoryConfig.class,BeanConfig.class,Application.class})
@WebAppConfiguration
@Transactional
public abstract class ContextControllerIntegrationTests {

	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	protected Filter springSecurityFilterChain;
	protected MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
        			.webAppContextSetup(wac)
        			.addFilters(springSecurityFilterChain)/*.alwaysExpect(status().isOk())*/
        			.build();
    }

//    @Test
//    public void verifiesHomePageLoads() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
}
