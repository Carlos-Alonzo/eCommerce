package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest
{
	private OrderController orderController;
	private User testUser;
	private Item testItem;
	private Cart testCart;
	private UserRepository userRepo = mock(UserRepository.class); //mocking the User repository class
	private OrderRepository orderRepo = mock(OrderRepository.class); //mocking the Cart repository class

	@Before
	public void setUp()
	{
		orderController = new OrderController();
		TestUtils.injectObjects(orderController, "userRepository", userRepo);
		TestUtils.injectObjects(orderController, "orderRepository", orderRepo);
		//need user
		testUser = new User("Carlos", "testPassword");
		testUser.setId(1l);
		//need item
		testItem = new Item();
		testItem.setId(1l);
		testItem.setName("FireTV");
		testItem.setDescription("Stream all your shows to your screen with FireTV");
		testItem.setPrice(BigDecimal.valueOf(44.99));
		//need cart
		testCart = new Cart();
		testCart.setUser(testUser);
		testCart.addItem(testItem);
		testUser.setCart(testCart);
	}

	@Test
	public void getOrdersForUserTest () throws Exception
	{
		//stubbing
		when(userRepo.findByUsername("Carlos")).thenReturn(testUser);
		final ResponseEntity<UserOrder> orderResponse = orderController.submit("Carlos");

		assertNotNull(orderResponse);
		assertEquals(200, orderResponse.getStatusCodeValue());
		UserOrder userOrder = orderResponse.getBody();
		assertNotNull(userOrder);
		assertNotNull(userOrder.getItems());
		assertEquals("Carlos", userOrder.getUser().getUsername());
	}
}
