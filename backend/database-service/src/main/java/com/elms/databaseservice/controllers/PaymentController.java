package com.elms.databaseservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.services.PaymentService;

@RestController
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	@Autowired
	AuthClient client;

	private Logger log = LoggerFactory.getLogger(PaymentController.class);

	
	@GetMapping("/instructor/{id}/course/{courseId}/reports")
	public ResponseEntity<Float> getCourseMonthlyReveneue(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,@PathVariable("courseId") int courseId) {
		log.info("Getting monthly Revenue");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return paymentService.showTotalRevenueByCourseId(courseId);
		else
		{
			log.error("User not authenticated");
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all-payments")
	public ResponseEntity<List<Payment>> getAllPayments() {
		log.info("Getting all payment details");
		
//		if(client.authorizeTheRequest(requestTokenHeader))
			return paymentService.getAllPayments();
//		else
//			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}
	
}
