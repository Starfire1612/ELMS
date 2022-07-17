package com.elms.databaseservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.services.PaymentService;

@RestController
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	
	@GetMapping("/instructor/{id}/course/{courseId}/reports")
	public ResponseEntity<Float> getCourseMonthlyReveneue(@PathVariable("courseId") int courseId) {
		return paymentService.showTotalRevenueByCourseId(courseId);
	}
	
	@GetMapping("/all-payments")
	public List<Payment> getAllPayments() {
		return paymentService.getAllPayments();
	}
	
//	@PostMapping()
//	public ResponseEntity<String> startPaymentProcess(Payment payment) {
//		return paymentService.createPayment(payment);
//	}
}
