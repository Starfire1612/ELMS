package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Certificate;
import com.elms.databaseservice.models.StudentCourseCertificateId;

public interface CertificateRepo extends JpaRepository<Certificate, StudentCourseCertificateId>,
		PagingAndSortingRepository<Certificate, StudentCourseCertificateId> {

}
