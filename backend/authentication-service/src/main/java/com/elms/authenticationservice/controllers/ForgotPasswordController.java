package com.elms.authenticationservice.controllers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.JwtRequest;
import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.service.AuthDatabaseService;
import com.elms.authenticationservice.service.EmailService;

@RestController
//@CrossOrigin("http://localhost:3000")

public class ForgotPasswordController {
	
	private Logger logger=LoggerFactory.getLogger(ForgotPasswordController.class);
	
	@Autowired
	AuthDatabaseService databaseService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//generating the otp mail for the registered user so they can can reset their password
	@GetMapping("/forgot-password/email/{email}/type/{type}")
	public ResponseEntity<String> getUserEmail(@PathVariable("type") String usertype,
			@PathVariable("email") String usermail) throws MalformedURLException, URISyntaxException {
		int otp = emailService.generateOtp();
		if (usertype.equalsIgnoreCase("student")) {
			Optional<Student> registeredStudent = Optional.ofNullable(databaseService.findStudentByEmail(usermail));
			if (registeredStudent.isEmpty())
				return new ResponseEntity<>("No such user email registered!", HttpStatus.NOT_FOUND);
			else {
				emailService.sendForgotPasswordOTPEmail(usermail, otp);
				HttpHeaders headers = new HttpHeaders();
				URL uri=new URL("http://localhost:8765/forgot-password");
				headers.setLocation(uri.toURI());
				headers.add("Otp",bCryptPasswordEncoder.encode(otp+""));
//				headers.set("Otp",bCryptPasswordEncoder.encode(otp+""));
//				headers.setAccessControlExposeHeaders(List.of("Otp"));
//				response.setHeader("Otp",bCryptPasswordEncoder.encode(otp+""));
				logger.info(headers.getAccessControlExposeHeaders().toArray().toString());

				return new ResponseEntity<>("Sent OTP mail to the registered email",headers, HttpStatus.OK);
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
	
	//updating the password of the user
	@PatchMapping("/forgot-password")
	public ResponseEntity<String> updatePassword(@RequestBody JwtRequest body){
		String userType=body.getType();
		String userEmail=body.getUseremail();
		String userPassword=body.getPassword();
		logger.debug(userEmail+" "+ userPassword+ " "+ userType);
		if (userType.equalsIgnoreCase("student")) {
			int n=databaseService.updateStudentPasswordByEmail(userEmail, userPassword);
			return  new ResponseEntity<>("Password successfully Updated "+n,HttpStatus.OK);
		} else {
			int n=databaseService.updateInsructorPasswordByEmail(userEmail, userPassword);
			return new ResponseEntity<>("Password successfully Updated "+n,HttpStatus.OK);
		}
	}
}
