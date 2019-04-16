package utils.email;

import constants.Constants;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

/**
 * Created by szpt_user045 on 25.09.2018.
 */
public class EmailSender {
    private final String username="checkman.szpt@gmail.com";
    private final String password="53721456";
    private final Properties props;

    private static EmailSender instance = new EmailSender();

    public static EmailSender getInstance() {
        return instance;
    }

    public EmailSender() {
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
        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject, Constants.ENCODE);
        message.setSentDate(new Date());
        message.setContent(text, "text/html; charset=utf-8");


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
