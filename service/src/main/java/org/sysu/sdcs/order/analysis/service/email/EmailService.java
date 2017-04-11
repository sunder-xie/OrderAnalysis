package org.sysu.sdcs.order.analysis.service.email;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.mail.util.MailSSLSocketFactory;

@Component
public class EmailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	// email properties from email.properties
	@Value("${email.sender}")
	private String sender;
	@Value("${email.password}")
	private String password;
	@Value("${email.recipient}")
	private String recipients;
	@Value("${email.host}")
	private String host;
	@Value("${email.port}")
	private String port;
	@Value("${email.auth}")
	private boolean isAuth;
	@Value("${email.ssl.enable}")
	private boolean isSSL;
	@Value("${email.ssl.trust.all.host}")
	private boolean isTrustAll;
	@Value("${email.protocol}")
	private String protocol;

	private Session session;
	private InternetAddress from;

	private static final String EMAIL_TYPE = "text/html;charset=UTF-8";

	@PostConstruct
	public void initial() {
		try {
			Properties prop = buildProperties();
			EmailAthentication athen = new EmailAthentication(sender, password);
			session = Session.getInstance(prop, athen);
			from = new InternetAddress(sender);
			LOGGER.info("Initial email configure success.");
		} catch (MessagingException ex) {
			LOGGER.error("Initial email configure fail.", ex);
		}
	}

	public void send(String title, String content) {
		send(recipients, title, content);
	}

	public void send(String emailRecipients, String title, String content) {
		try {
			MimeMessage message = buildMessage(emailRecipients, title, content);
			Transport.send(message);
			LOGGER.info("Send email success.");
		} catch (Exception ex) {
			LOGGER.error("Send email fail.", ex);
		}
	}

	private MimeMessage buildMessage(String emailRecipients, String title, String content)
			throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(from);
		message.setRecipients(RecipientType.TO, getAddress(emailRecipients));
		message.setSubject(title);
		message.setContent(content, EMAIL_TYPE);
		return message;
	}

	private InternetAddress[] getAddress(String recipientsList) throws AddressException {
		String[] allRecipient = recipientsList.split(";");
		InternetAddress[] allAddress = new InternetAddress[allRecipient.length];
		try {
			for (int i = 0; i < allRecipient.length; i++) {
				InternetAddress address = new InternetAddress(allRecipient[i]);
				allAddress[i] = address;
			}
			LOGGER.info("Initial recipient address success.");
		} catch (Exception ex) {
			LOGGER.error("Initial recipient address fail.", ex);
		}
		return allAddress;
	}

	private Properties buildProperties() {
		Properties prop = new Properties();
		try {
			prop.put("mail.smtp.host", host);
			prop.put("mail.transport.protocol", protocol);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", isAuth);
			prop.put("mail.user", sender);
			prop.put("mail.password", password);
			prop.put("mail.smtp.ssl.enable", isSSL);
			MailSSLSocketFactory factory = new MailSSLSocketFactory();
			factory.setTrustAllHosts(isTrustAll);
			prop.put("mail.smtp.ssl.socketFactory", factory);
			LOGGER.info("Initial email property success.");
		} catch (GeneralSecurityException ex) {
			LOGGER.error("Initial email property fail.", ex);
		}
		return prop;
	}
}
