package com.example.demo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.example.demo.logging.LogSendRequest;
import com.example.demo.model.persistence.UserOrder;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController
{
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	private LogSendRequest splunkEventLogger = new LogSendRequest();

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) throws IOException
	{
		User user = userRepository.findByUsername(username);
		if(user == null)
		{
			splunkEventLogger.setBody("Order Submit  attempt failure, no such user: "+ this.getClass().getName());
			HttpResponse logResponse= splunkEventLogger.executePost();
			log.info("Attempt to log Order Submit  attempt failure event to Splunk response: "
					         + logResponse.getStatusLine().getStatusCode()
					         + ": "
					         + logResponse.getStatusLine().getReasonPhrase());
//			log.error("User not found to submit order", username);
			return ResponseEntity.notFound().build();
		}
		UserOrder userOrder = UserOrder.createFromCart(user.getCart());
		orderRepository.save(userOrder);
		splunkEventLogger.setBody("Order Submit  attempt success,: "+ this.getClass().getName());
		HttpResponse logResponse= splunkEventLogger.executePost();
		log.info("Attempt to log Order Submit  attempt success event to Splunk response: "
				         + logResponse.getStatusLine().getStatusCode()
				         + ": "
				         + logResponse.getStatusLine().getReasonPhrase());
		return ResponseEntity.ok(userOrder);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
