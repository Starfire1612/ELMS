package com.elms.authenticationservice.service;

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

import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.Student;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private Logger log=LoggerFactory.getLogger(JwtUserDetailsService.class);
	@Autowired
	AuthDatabaseService service;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Override
	//username contains both useremail and user type seperated by a ":"
	public UserDetails loadUserByUsername(String useremailAndType) throws UsernameNotFoundException {
		log.info("User Details service class"+useremailAndType);
		String usermail=useremailAndType.split(":")[0];
		String userType=useremailAndType.split(":")[1];
		log.info(usermail+":"+userType);
		if(userType.equalsIgnoreCase("student")) {
			log.info("Student service class");
		Student user = service.findStudentByEmail(usermail);
		if (user == null) {

			log.warn("instructor not found with this user name");
			throw new UsernameNotFoundException("Student not found with username: " + usermail);
		}
		return new User(user.getStudentEmail(), bcryptEncoder.encode(user.getStudentPassword()),
				new ArrayList<>());
		}
		else {
			Instructor user = service.findInstructorByEmail(usermail);
			if (user == null) {
				log.warn("instructor not found with this user name");
				throw new UsernameNotFoundException("Instructor not found with username: " + usermail);
			}
			return new User(user.getInstructorEmail(), bcryptEncoder.encode(user.getInstructorPassword()),
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
