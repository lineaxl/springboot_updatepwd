package com.qf.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Map;
import java.util.Properties;

public class SendEmail {
	public static void sendEmail(Map<String, Object> map) {
		try {
			// 创建Properties 类用于记录邮箱的一些属性
			final Properties props = new Properties();
			// SMTP发送邮件，必须进行身份验证
			props.put("mail.smtp.auth", "true");
			// SMTP服务器
			props.put("mail.smtp.host", "smtp.qq.com");
			// QQ邮箱的端口号
			props.put("mail.smtp.port", "587");
			// 邮箱账号
			props.put("mail.user", "945300605@qq.com");
			// 邮箱生成的授权码
			props.put("mail.password", "vheyruhyufvbbfbc");
			// 构建授权信息，用于进行SMTP进行身份验证
			Authenticator authenticator = new Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					// 用户名、密码
					String userName = props.getProperty("mail.user");
					String password = props.getProperty("mail.password");
					return new PasswordAuthentication(userName, password);
				}
			};
			// 使用环境属性和授权信息，创建邮件会话
			Session mailSession = Session.getInstance(props, authenticator);
			// 创建邮件消息
			MimeMessage message = new MimeMessage(mailSession);
			// 设置发件人
			InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
			message.setSubject((String) map.get("title"));
			message.setFrom(form);

			// 设置收件人的邮箱
			InternetAddress to = new InternetAddress((String) map.get("email"));
			message.setRecipient(RecipientType.TO, to);

			// 设置邮件的内容体
			message.setContent(map.get("sBuilder").toString(), "text/html;charset=UTF-8");

			// 发送邮件
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
