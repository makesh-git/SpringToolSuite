package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotificatioService {
	
	
	private JavaMailSender javaMailSender;

	@Autowired
	public NotificatioService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNotification(String to, String sub, String msg) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setSubject(sub);
		mail.setText(msg);
		try {
			javaMailSender.send(mail);
			System.out.println("Email sent successfully !");
		}
		
		catch (Exception e) {

			System.out.println("!!!   Error in Sending Mail   !!!");
			System.out.println("This may due to invalid email id or password");
			System.out.println("---------xxxxxxx---------");
			System.out.println();
		}
		
	}

}
