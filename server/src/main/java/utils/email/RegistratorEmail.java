package utils.email;

import utils.LanguageBase;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
public class RegistratorEmail {
    final static EmailSender sender = EmailSender.getInstance();

    static final String subject = LanguageBase.getBase().get("registration.email.subject");
    static final String text = LanguageBase.getBase().get("registration.email.text");
    public static void sendEmail(String email, String link, String password) {
        try {
            sender.sendEmail(email, subject, String.format(text, link, link, password));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
