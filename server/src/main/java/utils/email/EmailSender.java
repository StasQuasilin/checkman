package utils.email;

import constants.Constants;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by szpt_user045 on 25.09.2018.
 */
public class EmailSender {

    private final Logger log = Logger.getLogger(EmailSender.class);
    private final String username="checkman.szpt@gmail.com";
    private final String password="53721456";
    private final Properties props;

    private static EmailSender instance = new EmailSender();

    public static EmailSender getInstance() {
        return instance;
    }

    private EmailSender() {
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    public void sendEmail(String recipient, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        InternetAddress recipientAddress = new InternetAddress(recipient);
        recipientAddress.validate();
        log.info("Address is valid");

        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, recipientAddress);
        message.setSubject(subject, Constants.ENCODING);
        message.setSentDate(new Date());
        message.setContent(text, "text/html; charset=utf-8");

        log.info("Send email \'" + subject + "\' for \'" + recipientAddress + "\'");
        Transport.send(message);
    }

    private Session createSession(){
        return Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
