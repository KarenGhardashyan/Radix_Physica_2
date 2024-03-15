package com.example.radix_physica.AddQuizAndQuestion;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class SendEmailExample {
    public static void sendEmail() {
        String apiKey = "YOUR_SENDGRID_API_KEY";

        Email from = new Email("example@example.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("test@example.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Response code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
            System.out.println("Response headers: " + response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendEmail();
    }
}
