package com.elms.authenticationservice.config;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.service.AuthDatabaseService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthDatabaseService authDatabaseService;
	private Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String usermail = authentication.getName().split(":")[0];
		String userType = authentication.getName().split(":")[1];

		log.info("Calling from custom auth provider: "+usermail  + ":" + userType + ": " + authentication.getCredentials().toString());
		if (userType.equalsIgnoreCase("student")) {
			log.info("Student service class");
			Student user= authDatabaseService.findStudentByEmail(usermail);
			if (user == null) {
				throw new UsernameNotFoundException("Student not found with username: " + usermail);
			} else {
				if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
					return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.emptyList());
				} else {
					throw new BadCredentialsException("Wrong password");
				}
			}
		} else {
			Instructor user = authDatabaseService.findInstructorByEmail(usermail);
			if (user == null) {
				throw new UsernameNotFoundException("Instructor not found with username: " + usermail);
			} else {
				if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
					return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.emptyList());
				} else {
					throw new BadCredentialsException("Wrong password");
				}
			}

		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
