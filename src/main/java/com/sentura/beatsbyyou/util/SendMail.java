package com.sentura.beatsbyyou.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SendMail {

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${mail.user}")
    private String mailUser;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.port}")
    private String mailPort;

    @Value("${mail.host}")
    private String mailHost;

    private Logger logger = LoggerFactory.getLogger(SendMail.class);

    public void sendEmail(String to,String cc,String subject,String body) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", mailHost);
        properties.setProperty("mail.smtp.port", mailPort);
        properties.setProperty("mail.smtp.user", mailUser);
        properties.setProperty("mail.smtp.password", mailPassword);
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser,
                        mailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            if (cc != null) {
                InternetAddress[] addresses = null;
                if (cc.contains(",")) {
                    String ccArray[] = cc.split(",");
                    addresses = new InternetAddress[ccArray.length];
                    for(int i=0;i<ccArray.length;i++){
                        addresses[i] = new InternetAddress(ccArray[i]);
                    }
                } else if(!cc.equals("")) {
                    addresses = new InternetAddress[1];
                    addresses[0] = new InternetAddress(cc);
                }
                message.addRecipients(Message.RecipientType.CC, addresses);
            }
            message.setSubject(subject);
            message.setContent(body, "text/html");
            Transport.send(message);
            System.out.println("Sent Email Successfully....");
            logger.info("Sent Email Successfully...");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
