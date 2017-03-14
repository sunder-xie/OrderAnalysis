package org.sysu.sdcs.order.analysis.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

import com.sun.mail.util.MailSSLSocketFactory;

public class EmailTest {
	
	@Test
	public void test() throws MessagingException, GeneralSecurityException {
		Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.user", "895273799@qq.com");
        props.put("mail.password", "vxtbxalufumwbeci");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        
     // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, auth);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress("895273799@qq.com");
        message.setRecipient(RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("测试邮件");

        // 设置邮件的内容体
        message.setContent("<a href='http://www.fkjava.org'>测试的HTML邮件</a>", "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);
	}
}
