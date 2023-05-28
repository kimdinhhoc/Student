package kimdinhhoc.student.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Autowired
	SpringTemplateEngine templateEngine;
	
	public void sendBirthdayEmail(String to, String name) {
		String subject = "Happy Birthday ! " + name;
		
		Context ctx = new Context();//thymeleaf
		ctx.setVariable("name", name);
		
		String body= templateEngine.process("email/hpbd.html", ctx);

		sendEmail(to, subject, body);
	}

	public void sendEmail(String to, String subject, 
			String body) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = 
					new MimeMessageHelper(message, 
							StandardCharsets.UTF_8.name());

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setFrom(fromEmail);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void testEmail() {
		String to = "mail_nguoi_nhan@gmail.com";
		String subject = "Tieu de email";
		String body = "<h1>Noi dung email</h1>";

		sendEmail(to, subject, body);
	}
}
