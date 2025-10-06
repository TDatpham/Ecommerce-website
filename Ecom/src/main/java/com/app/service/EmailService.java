package com.app.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private  String u_name;

	@Value("${spring.mail.password}")
	private  String u_pass;

	public  boolean send(String to, String from, String subject, String text) {
		
		boolean flag = false;
		
		
		// logic 
		// smtp properties 
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		
		String userName = u_name;
		String password = u_pass;
	
		// session
		
		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(userName, password);
			}
		});
		
		
		try {
			
			Message message = new MimeMessage(session);
			
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			message.setText(text);
			
			Transport.send(message);
			flag = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		
		return flag;
	}
}
