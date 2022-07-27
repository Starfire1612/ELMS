package com.elms.authenticationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private Logger log = LoggerFactory.getLogger(EmailService.class);
	@Autowired
	JavaMailSender javaMailSender;

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

	}
}
