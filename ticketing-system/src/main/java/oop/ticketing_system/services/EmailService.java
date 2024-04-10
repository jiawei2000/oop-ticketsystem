package oop.ticketing_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private String subject = "Your Ticket Confirmation";
    private String line1 = "Dear valued customer, \n\n";
    private String line2 = "We are pleased to inform you that your ticket purchase has been successfully confirmed. Below are the details of your ticket: \n\n";
    private String line3 = "Thank you for choosing to attend our event. You can view your tickets by visiting our website at <a href=\"http://localhost:5173/login\">TicketTime</a>.\n\n";
    private String line4 = "If you have any questions or require further assistance, please do not hesitate to contact us.\n\n";
    private String line5 = "Best regards,\nTicketTime";

    public void sendEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(this.subject);
        String fullEmailBody = line1 +line2 + body + "\n" + line3 + line4 + line5;
        message.setText(fullEmailBody);
        mailSender.send(message);
    }
}
