package com.elms.authenticationservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.authenticationservice.config.JwtTokenUtil;
import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.JwtRequest;
import com.elms.authenticationservice.models.JwtResponse;
import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.service.AuthDatabaseService;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
public class JwtAuthenticationController {
	private Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	AuthDatabaseService databaseService;
//
//	@Autowired
//	private JwtUserDetailsService userDetailsService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		log.info(authenticationRequest.getPassword() + "\t" + authenticationRequest.getUseremail() + "\t"
				+ authenticationRequest.getType());

		Authentication s = authenticate(authenticationRequest.getUseremail() + ":" + authenticationRequest.getType(),
				authenticationRequest.getPassword());

		log.info(s.getName() + ":" + s.getDetails());
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getUseremail(),
				authenticationRequest.getType());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value = "/authorize/{id}")
	public boolean authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable int id) {
		System.out.println("Inside authorize ==============" + requestTokenHeader);
		String jwtToken = null;
		String userName = null, userType = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			System.out.println("JWT Token =======================" + jwtToken);
			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
				userType = jwtTokenUtil.getUserTypeFromToken(jwtToken);

				if (userType.equalsIgnoreCase("student")) {
					Student s = databaseService.findStudentByEmail(userName);
					log.info("Token Usename" + userName + " :  \t" + "UserName from Db" + s.getEmail());
					return (s.getId()==id);
				} else {
					Instructor i = databaseService.findInstructorByEmail(userName);
					log.info("Token Usename" + userName + " :  \t" + "UserName from Db" + i.getEmail());
					return (i.getId()==id);
				}
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				return false;
			}
		}
		return userName != null;

	}

	private Authentication authenticate(String username, String password) throws Exception {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}