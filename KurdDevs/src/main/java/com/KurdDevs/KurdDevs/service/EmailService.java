package com.KurdDevs.KurdDevs.service;

import com.KurdDevs.KurdDevs.Repo.UserRepository;
import com.KurdDevs.KurdDevs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class EmailService {

    private final UserRepository userRepository;

    @Autowired
    public EmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void sendActivationEmail(String recipientEmail, String activationToken) {
        User user = userRepository.findByEmail(recipientEmail);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kurddevs1@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Account Activation");
            String emailText = "<html><body>"
                    + "<b><h1>Hi Dear " + user.getUsername() + "</h1></b>"
                    + "<p>Thank you for registering with our platform. To activate your account, please click on the following link:</p>"
                    + "<a href=\"https://kurddevs.onrender.com/activate?activationToken=" + URLEncoder.encode(activationToken, StandardCharsets.UTF_8) + "\">Activate Account</a>"
                    + "<p>We appreciate your interest and look forward to providing you with a great user experience.</p>"
                    + "<p>Sincerely,</p>"
                    + "<p><b>KurdDevsTeam(KDT)</b></p>"
                    + "</body></html>";

            message.setContent(emailText, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send activation email", e);
        }
    }
}