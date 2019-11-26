package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest
{
	//test resources
	private CartController cartController;
	private User testUser;
	private Item testItem;
	private Cart testCart;
	//Mockery
	private UserRepository userRepo = mock(UserRepository.class); //mocking the User repository class
	private CartRepository cartRepo = mock(CartRepository.class); //mocking the Cart repository class
	private ItemRepository itemRepo = mock(ItemRepository.class); //mocking the Item repository class

	@Before
	public void setUp()
	{
		cartController = new CartController();
		TestUtils.injectObjects(cartController, "userRepository", userRepo);
		TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
		TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
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
		testUser.setCart(testCart);
	}

	@Test
	public void addToCartTest() throws Exception
	{
		//stubbing
		when(userRepo.findByUsername("Carlos")).thenReturn(testUser);
		when(itemRepo.findById(1l)).thenReturn(java.util.Optional.ofNullable(testItem));

		ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
		modifyCartRequest.setUsername(testUser.getUsername());
		modifyCartRequest.setItemId(testItem.getId());
		modifyCartRequest.setQuantity(3);

		final ResponseEntity<Cart> cartResponse = cartController.addTocart(modifyCartRequest);

		assertNotNull(cartResponse);
		assertEquals(200, cartResponse.getStatusCodeValue());
		Cart cart = cartResponse.getBody();
		assertNotNull(cart);
		assertEquals(1, cartResponse.getBody().getUser().getId());
		assertEquals("Carlos", cart.getUser().getUsername());
		assertEquals(3, cart.getItems().size());
	}

	@Test
	public void RemoveFromCartTest() throws Exception
	{
		//stubbing
		when(userRepo.findByUsername("Carlos")).thenReturn(testUser);
		when(itemRepo.findById(1l)).thenReturn(java.util.Optional.ofNullable(testItem));

		ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
		modifyCartRequest.setUsername(testUser.getUsername());
		modifyCartRequest.setItemId(testItem.getId());
		modifyCartRequest.setQuantity(3);

		final ResponseEntity<Cart> cartResponse = cartController.removeFromcart(modifyCartRequest);

		assertNotNull(cartResponse);
		assertEquals(200, cartResponse.getStatusCodeValue());
		Cart cart = cartResponse.getBody();
		assertNotNull(cart);
		assertEquals(0, cart.getItems().size());
	}

}
