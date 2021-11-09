package com.Pos10Max.POS10APIMAX.Shared;

import com.Pos10Max.POS10APIMAX.Pos10ApimaxApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

//	@Autowired
//	private JavaMailSender mailSender;
	
//	@Autowired
//	private Environment environment;
	
public void sendSimpleEmail(String toEmail,String body,String subject) {

//	JavaMailSender javaMailSender = mailSender.send(null);

	SimpleMailMessage message=new SimpleMailMessage();

	message.setFrom("johncenaphone26@gmail.com");

	message.setTo(toEmail);
	message.setText(body);

	message.setSubject(subject);

//	Pos10ApimaxApplication.javaMailSender().send(message);

	System.out.println("Mail Send...");

}
}
