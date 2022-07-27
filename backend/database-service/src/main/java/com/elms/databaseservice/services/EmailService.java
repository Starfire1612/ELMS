package com.elms.databaseservice.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.elms.databaseservice.models.StudentCourse;

@Service
public class EmailService {
	private Logger log = LoggerFactory.getLogger(EmailService.class);
	@Autowired
	JavaMailSender javaMailSender;



	public void sendMailWithAttachment(StudentCourse studentCourse) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		String studentName = studentCourse.getStudentId().getStudentName();
		String courseName = studentCourse.getCourseId().getCourseName();
		String filename = studentName + "." + courseName+".pdf";
		helper.setSubject("Congratulations !!!");
		helper.setFrom("rohansohan998@gmail.com");
		helper.setTo(studentCourse.getStudentId().getStudentEmail());

		helper.setText(
				"<b>Dear friend</b>," + "<br><i>Please find the course completion certificate in the  attachment.</i>",
				true);

		FileSystemResource file = new FileSystemResource(new File(filename));
		helper.addAttachment("Certificate.pdf", file);
		javaMailSender.send(message);
		log.info("Sending Attachment email ");
	}

	public void sendForgotPasswordOTPEmail(String to,int otp) {
		log.info("Sending Mail");
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("rohoansohan998@gmail.com");
		mailMessage.setTo(to);
		mailMessage.setSubject("Forget Password");
		mailMessage.setText("This is your Otp"+" : "+otp+"");
		javaMailSender.send(mailMessage);
		log.info("Sending OTp email "+ otp);

	}
	
	public int generateOtp() {
		

		log.info("Generating OTP");
		int max = 9999;
		int min = 1000;
		int range = max - min + 1;

		// generate random numbers within 1 to 10
		int otp = (int) (Math.random() * range) + min;
		return otp;

	}}
