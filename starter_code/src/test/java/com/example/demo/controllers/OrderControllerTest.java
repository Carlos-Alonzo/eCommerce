package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest
{

	private UserController userController;
	private UserRepository userRepo = mock(UserRepository.class); //mocking the User repository class
	private CartRepository cartRepo = mock(CartRepository.class); //mocking the Cart repository class
	private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

	@Before
	public void setUp()
	{
		userController = new UserController();
		//reason for these injections is because these are object required for a userController
		TestUtils.injectObjects(userController, "userRepository", userRepo);
		TestUtils.injectObjects(userController, "cartRepository", cartRepo);
		TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
	}

	@Test
	public void createUserHappyPath () throws Exception
	{
		//stubbing
		when(encoder.encode("testPassword")).thenReturn("ThisIsHashed");
		CreateUserRequest r = new CreateUserRequest();
		r.setUsername("Test");
		r.setPassword("testPassword");
		r.setConfirmPassword("testPassword");

		final ResponseEntity<User> response = userController.createUser(r);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());

		User u = response.getBody();
		assertNotNull(u);
		assertEquals(0, u.getId());
		assertEquals("Test", u.getUsername());
		assertEquals("ThisIsHashed", u.getPassword());

	}
}
