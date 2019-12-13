package com.example.demo.controllers;

import java.io.IOException;
import java.util.Optional;

import com.example.demo.logging.LogSendRequest;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController
{
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private LogSendRequest splunkEventLogger = new LogSendRequest();

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable @Valid Long id) { 		return ResponseEntity.of(userRepository.findById(id)); }
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable @Valid String username)
	{
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody @Valid  CreateUserRequest createUserRequest) throws IOException
	{
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		log.info("User name set with: ", createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);

		if(createUserRequest.getPassword().length()< 7 || !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword()))
		{
			splunkEventLogger.setBody("Unsuccessful user creation attempt: "+ this.getClass().getName());
			HttpResponse logResponse= splunkEventLogger.executePost();
			log.info("Attempt to log unsuccessful Create User event to Splunk response: "
					         + logResponse.getStatusLine().getStatusCode()
					         + ": "
					         + logResponse.getStatusLine().getReasonPhrase());
//			log.error("Error with user password. Cannot create user: ", createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

		userRepository.save(user);
		splunkEventLogger.setBody("Successful user creation attempt: "+ this.getClass().getName());
		HttpResponse logResponse= splunkEventLogger.executePost();
		log.info("Attempt to log successful Create User event to Splunk response: "
				         + logResponse.getStatusLine().getStatusCode()
				         + ": "
				         + logResponse.getStatusLine().getReasonPhrase());
		return ResponseEntity.ok(user);
	}
	
}
