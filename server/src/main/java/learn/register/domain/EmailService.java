package learn.register.domain;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")  // Fetch the API key from application.properties
    private String sendGridApiKey;
    @Value("${sendgrid.sender.email}")  // Fetch the sender's email from application.properties
    private String senderEmail;

    public void sendEmail(String toEmail, String subject, String body) throws IOException {
        Email from = new Email(senderEmail);  // Sender's email
        Email to = new Email(toEmail);                    // Recipient's email
        Content content = new Content("text/plain", body); // Email content
        Mail mail = new Mail(from, subject, to, content);  // Construct the email

        SendGrid sg = new SendGrid(sendGridApiKey);        // Use API key from properties
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);          // Send the email
            System.out.println("Email sent, status: " + response.getStatusCode());
        } catch (IOException ex) {
            System.out.println("Error sending email: " + ex.getMessage());
            throw ex;
        }
    }
}