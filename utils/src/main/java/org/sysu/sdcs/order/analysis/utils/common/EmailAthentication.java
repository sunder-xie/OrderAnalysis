package org.sysu.sdcs.order.analysis.utils.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAthentication extends Authenticator {
	private String sender;
	private String password;

	public EmailAthentication(String sender, String password) {
		this.sender = sender;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(sender, password);
	}
}
