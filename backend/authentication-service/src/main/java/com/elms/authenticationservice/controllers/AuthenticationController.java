package com.elms.authenticationservice.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jcajce.BCFKSLoadStoreParameter.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.authenticationservice.security.SecurityConfig;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@RestController
public class AuthenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	@GetMapping("/authenticate")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		LOGGER.info("Starting Authentication Process");
		HashMap<String, String> map = new HashMap<>();

		LOGGER.info(authHeader);

		String token = authHeader.split(" ")[1];

		String UserName = getUser(token);
		String awtToken = generateJwt(UserName);
		LOGGER.info("User name after decoding :" + UserName);

		LOGGER.info("Ending Authentication Process");
		map.put("token", awtToken);
		return map;

	}

	private String getUser(String authHeader) {
		Base64.Decoder decoder = Base64.getDecoder();
		// Decoding string
		String dStr = new String(decoder.decode(authHeader));
		return dStr.split(":")[0];
	}

	private String generateJwt(String user) {
		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);

		// Set the token issue time as current time
		builder.setIssuedAt(new Date());

		// Set the token expiry as 20 minutes from now
		builder.setExpiration(new Date((new Date()).getTime() + 1200000));
		builder.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, "secretkey");

		String token = builder.compact();

		return token;

	}
}
