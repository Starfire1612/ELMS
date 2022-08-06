package com.elms.registrationservice.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.registrationservice.models.Instructor;
import com.elms.registrationservice.models.RegisterUser;
import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.services.AuthDatabaseService;
import com.elms.registrationservice.services.EmailService;
import com.elms.registrationservice.services.RegisterService;

//@CrossOrigin("http://localhost:3000")
@RestController
public class RegistrationController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
	RegisterService service;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	EmailService emailService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthDatabaseService databaseService;

	@GetMapping(path = "/students")
	public List<Student> fetchAllStudents() {
		return service.getAllStudents();
	}

//	rehistering a new user based on the user type
	@PostMapping(path = "/register-user/type/{type}")
	public ResponseEntity<String> registerUser(@PathVariable("type") String type, @RequestBody RegisterUser user) {
		try {
			logger.info(user.toString());

			if (type.equalsIgnoreCase("student")) {
				Student s = new Student();
				s.setName(user.getUsername());
				s.setEmail(user.getUseremail());
				s.setPassword(encoder.encode(user.getPassword()));
				return service.registerStudent(s);
			} else {
				Instructor i = new Instructor();
				i.setName(user.getUsername());
				i.setEmail(user.getUseremail());
				i.setPassword(encoder.encode(user.getPassword()));
				return service.registerInstructor(i);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Registration Failed", HttpStatus.BAD_REQUEST);
		}
	}

// sending an email verification mail to the user before registering them in the databse
	@GetMapping("/registration/email/{email}/type/{type}")
	public ResponseEntity<String> verifyUserMail(@PathVariable("type") String usertype,
			@PathVariable("email") String usermail) {
		int otp = emailService.generateOtp();
		logger.info("Verifying Email");
		if (usertype.equalsIgnoreCase("student")) {
			Optional<Student> registeredStudent = Optional.ofNullable(databaseService.findStudentByEmail(usermail));
			if (!registeredStudent.isEmpty()) {
				logger.error("User already exists");
				return new ResponseEntity<>("Email already registered!", HttpStatus.BAD_REQUEST);
			} else {
				emailService.sendVerificationMail(usermail, otp);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Otp", bCryptPasswordEncoder.encode(otp + ""));
				headers.setAccessControlExposeHeaders(List.of("Otp"));
				logger.info("Otp Sent");
				return new ResponseEntity<>("Sent OTP mail to the registered email", headers, HttpStatus.OK);
			}
		} else {
			Optional<Instructor> registeredInstructor = Optional
					.ofNullable(databaseService.findInstructorByEmail(usermail));
			if (!registeredInstructor.isEmpty()) {

				logger.error("User already exists");
				return new ResponseEntity<>("Email already registered!", HttpStatus.BAD_REQUEST);
			} else {
				emailService.sendVerificationMail(usermail, otp);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Otp", otp + "");
				logger.info("OTP Send");
				return new ResponseEntity<>("Sent OTP mail to the registered email.", HttpStatus.FOUND);
			}
		}
	}
}
