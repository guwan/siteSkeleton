/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guwan.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.guwan.CustomRepositoryConfig;
import com.guwan.domain.User;
import com.guwan.repository.UserRepository;

/**
 * Intergration test showing the basic usage of {@link UserRepository}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = CustomRepositoryConfig.class)
// @ActiveProfiles("jdbc") // Uncomment @ActiveProfiles to enable the JDBC Implementation of the custom repository
public class UserRepositoryCustomizationTests {

	@Autowired UserRepository repository;

	/**
	 * Tests inserting a user and asserts it can be loaded again.
	 */
	@Test
	public void testInsert() {

		User user = new User();
		user.setEmail("email@qq.com");

		user = repository.save(user);

		assertEquals(user, repository.findOne(user.getId()));
	}

	@Test
	public void saveAndFindByNameAndFindByEmail() {

		User user = new User();
		user.setUsername("email");
		user.setEmail("email@qq.com");

		user = repository.save(user);

		List<User> users = repository.findByUsername("email");

		assertNotNull(users);
		assertTrue(users.contains(user));

		User reference = repository.findByEmail("email@qq.com");
		assertEquals(user, reference);
	}

	/**
	 * Test invocation of custom method.
	 */
	@Test
	public void testCustomMethod() {

		User user = new User();
		user.setUsername("username");

		user = repository.save(user);

		List<User> users = repository.myCustomBatchOperation();

		assertNotNull(users);
		assertTrue(users.contains(user));
	}
	/**
	 * Test invocation of custom method.
	 */
	@Test
	public void testFindUsersByNameQuery() {

		User user = new User();
		user.setUsername("username");

		user = repository.save(user);

		List<User> users = repository.findUsersByNameQuery("username");

		assertNotNull(users);
		assertTrue(users.contains(user));
	}
	@Test
	public void testFindUserByNameQuery() {

		User user = new User();
		user.setUsername("username");

		user = repository.save(user);

		User reference = repository.findUserByNameQuery("username");
		
		assertNotNull(reference);
		assertEquals(user, reference);
	}
}
