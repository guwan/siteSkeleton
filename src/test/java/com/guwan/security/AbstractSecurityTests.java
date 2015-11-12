package com.guwan.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.guwan.Application;
import com.guwan.CustomRepositoryConfig;
import com.guwan.domain.Authority;
import com.guwan.domain.User;
import com.guwan.repository.UserRepository;
import com.guwan.support.BCryptEncoder;

@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = CustomRepositoryConfig.class)
@WebAppConfiguration
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractSecurityTests {

  @Autowired
  protected WebApplicationContext context;

  @Autowired
  protected Filter springSecurityFilterChain;

  @Autowired
  protected UserRepository repository;
  
  @Autowired
  protected BCryptEncoder bCryptEncoder;

  protected MockMvc mvc;

  @Before
  public void setup() {
      mvc = MockMvcBuilders
              .webAppContextSetup(context)
              .addFilters(springSecurityFilterChain)
              .build();
  }
  @Test
  public void createUser(){
	  
		User user=new User();
		user.setUsername("user");
		user.setPassword(bCryptEncoder.encode("password"));
		ArrayList<Authority> a=new ArrayList<Authority>();
		a.add(new Authority(user,"USER"));
		user.setAuthorities(a);
		repository.save(user);

		User reference = repository.findUserByNameQuery("username");
		
		assertNotNull(reference);
		assertEquals(user, reference);
  }
}