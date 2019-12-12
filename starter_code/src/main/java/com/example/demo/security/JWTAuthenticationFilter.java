package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.example.demo.logging.LogSendRequest;
import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.demo.security.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private LogSendRequest splunkEventLogger;
	private AuthenticationManager authenticationManager;
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
//		splunkEventLogger = new LogSendRequest();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
	{
		try
		{
			User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
			return authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
		}
		catch (IOException e) { 			throw new RuntimeException(e); }
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException
	{
		splunkEventLogger = new LogSendRequest("Successful login attempt: "+ this.getClass().getName());
		HttpResponse logResponse= splunkEventLogger.executePost();
		log.info("Successful Login Log send request response code: "
				+ logResponse.getStatusLine().getStatusCode()
				+ ": "
				+ logResponse.getStatusLine().getReasonPhrase());

		String token = JWT.create()
				               .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
				               .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				               .sign(HMAC512(SECRET.getBytes()));
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
	{
		splunkEventLogger = new LogSendRequest("Unsuccessful login attempt: "+ this.getClass().getName());
		HttpResponse logResponse= splunkEventLogger.executePost();
		log.info("Unsuccessful Login Log send request response code: "
				+ logResponse.getStatusLine().getStatusCode()
				+ ": "
				+ logResponse.getStatusLine().getReasonPhrase());
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
