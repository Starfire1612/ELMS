package com.example.authenticationexample.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.authenticationexample.models.Instructor;
import com.example.authenticationexample.models.Student;
import com.example.authenticationexample.service.AuthDatabaseService;
import com.example.authenticationexample.service.EmailService;

@RestController
@CrossOrigin("http://localhost:3000")
public class ForgotPasswordController {

	@Autowired
	AuthDatabaseService databaseService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

	@GetMapping("/forgot-password/email/{email}/type/{type}")
	public ResponseEntity<String> getUserEmail(@PathVariable("type") String usertype,
			@PathVariable("email") String usermail) {
		int otp = emailService.generateOtp();
		if (usertype.equalsIgnoreCase("student")) {
			Optional<Student> registeredStudent = Optional.ofNullable(databaseService.findStudentByEmail(usermail));
			if (registeredStudent.isEmpty())
				return new ResponseEntity<>("No such user email registered!", HttpStatus.NOT_FOUND);
			else {
				emailService.sendForgotPasswordOTPEmail(usermail, otp);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Otp",bCryptPasswordEncoder.encode(otp+""));
				headers.setAccessControlExposeHeaders(List.of("Otp"));
				
				return new ResponseEntity<>("Sent OTP mail to the registered email", headers, HttpStatus.OK);
			}
		} else {
			Optional<Instructor> registeredInstructor = Optional
					.ofNullable(databaseService.findInstructorByEmail(usermail));
			if (registeredInstructor.isEmpty())
				return new ResponseEntity<>("No such user email registered!", HttpStatus.NOT_FOUND);
			else {
				emailService.sendForgotPasswordOTPEmail(usermail, otp);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Otp", otp + "");
				return new ResponseEntity<>("Sent OTP mail to the registered email.", HttpStatus.FOUND);
			}
		}
	}
}
