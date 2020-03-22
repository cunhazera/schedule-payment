package com.schedule.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class MailService {

    public void sendMail(String toAddress, String mailContent) {
        Email from = new Email("schedule-paymentp@payment.com");
        String subject = "Your transaction result!";
        Email to = new Email(toAddress);
        Content content = new Content("text/plain", mailContent);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sendGrid = new SendGrid(System.getenv("SEND_GRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

}
