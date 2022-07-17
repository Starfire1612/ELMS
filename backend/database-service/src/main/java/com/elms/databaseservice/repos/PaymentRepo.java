package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.StudentCoursePaymentId;

public interface PaymentRepo extends JpaRepository<Payment, StudentCoursePaymentId>,
		PagingAndSortingRepository<Payment, StudentCoursePaymentId> {

	@Query(nativeQuery = true,value = "select sum(payment_amount) from payment where course_id=:courseId"
			+ " and extract(MONTH from payment_date)=extract(MONTH from SYSDATE()) and "
			+ "extract(YEAR from payment_date)=extract(YEAR from SYSDATE())")
	public float getTotalRevenueByCourseId(int courseId);
}
