package com.example.authenticationexample.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authenticationexample.config.JwtRequestFilter;
import com.example.authenticationexample.models.Instructor;
import com.example.authenticationexample.models.Student;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private Logger log=LoggerFactory.getLogger(JwtUserDetailsService.class);
	@Autowired
	AuthDatabaseService service;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Override
	//username contains both useremail and user type seperated by a ":"
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("User Details service class"+username);
		String usermail=username.split(":")[0];
		String userType=username.split(":")[1];
		log.info(usermail+":"+userType);
		if(userType.equalsIgnoreCase("student")) {
			log.info("Student service class");
		Student user = service.findStudentByEmail(usermail);
		if (user == null) {
			throw new UsernameNotFoundException("Student not found with username: " + username);
		}
		return new User(user.getEmail(), bcryptEncoder.encode(user.getPassword()),
				new ArrayList<>());
		}
		else {
			Instructor user = service.findInstructorByEmail(usermail);
			if (user == null) {
				throw new UsernameNotFoundException("Instructor not found with username: " + username);
			}
			return new User(user.getEmail(), bcryptEncoder.encode(user.getPassword()),
					new ArrayList<>());
		}
//		if ("radhika".equals(username)) {
//			return new User("radhika", "$2a$10$T.w.mF3ZeBfNfoLpDVx7NOKOeEg5O0v.TuiSNiFPYhRJja8p85Oai",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
	}
	
}
