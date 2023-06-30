package com.KurdDevs.KurdDevs.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    public void sendActivationEmail(String recipientEmail, String activationToken) {
        // Configure the email properties and session
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kurddevs1@gmail.com", "kpubhdmhrytjctnw");
            }
        });

        try {
            // Compose the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("KurdDevsTeam@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Account Activation");

            String emailText = "<html><body>"
                    + "<b><p>Hello Dear!,</p></b>"
                    + "<p>Thank you for registering with our platform. To activate your account, please click on the following link:</p>"
                    + "<a href=\"http://localhost:8080/activate?activationToken=" + activationToken + "\">Activate Account</a>\n\n"
                    + "<p>We appreciate your interest and look forward to providing you with a great user experience.</p>"
                    + "<p> sincerely </p>"
                    + "<p><b>KurdDevsTeam(KDT)</b></p>"
                    + "</body></html>";
            message.setContent(emailText, "text/html");

            // Send the email
            Transport.send(message);
            System.out.println("Activation email sent successfully");
        } catch (MessagingException e) {
            System.err.println("Error sending activation email: " + e.getMessage());
        }
    }
}
