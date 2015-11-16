package com.guwan.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
import com.guwan.BeanConfig;
import com.guwan.CustomRepositoryConfig;
import com.guwan.controller.AdminController;
import com.guwan.domain.Authority;
import com.guwan.domain.User;
import com.guwan.repository.UserRepository;
import com.guwan.support.BCryptEncoder;

//@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes ={ CustomRepositoryConfig.class,BeanConfig.class,Application.class})
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
  @Before
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
  @After
  public void teardown(){
	  repository.deleteByUsername("user");
	  User reference = repository.findByUsername("user");
	  assertNull(reference);
	  repository.deleteByUsername("admin");
	  User reference2 = repository.findByUsername("admin");
	  assertNull(reference2);
  }
}