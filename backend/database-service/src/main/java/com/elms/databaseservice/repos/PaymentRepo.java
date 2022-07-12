package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.StudentCoursePaymentId;

public interface PaymentRepo extends JpaRepository<Payment, StudentCoursePaymentId>,
		PagingAndSortingRepository<Payment, StudentCoursePaymentId> {

}
