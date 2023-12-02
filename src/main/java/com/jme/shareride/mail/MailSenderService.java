package com.jme.shareride.mail;

import com.jme.shareride.requests.contactus.ContactSupportRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;


    public void sendWelcomeMail(String toEmail,String username) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(new InternetAddress("samuelab846@gmail.com","Rideshare"));
        helper.setTo(toEmail);
        helper.setSubject(username + ",welcome to rideshare");
        String body = getBody(username);
        helper.setText(body,true);
        javaMailSender.send(mimeMessage);
        System.out.println("welcome email has been sent to " + toEmail);

    }

        public void sendContactEmail(ContactSupportRequest supportRequest) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(new InternetAddress(supportRequest.getEmail()));
        helper.setTo("samuelab846@gmail.com");
        helper.setSubject("support request");
        String body = getSupportTemplate(supportRequest.getEmail(),supportRequest.getName(),supportRequest.getMobileNumber(),supportRequest.getMessage());
        helper.setText(body,true);
        javaMailSender.send(mimeMessage);
        System.out.println("support email was sent from " + supportRequest.getEmail());

    }





    private static String getBody(String username) {
        var body ="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Welcome to Rise Share!</title>\n" +
                "    <style>\n" +
                "        /* CSS Styles */\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f5f5f5;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            font-size: 24px;\n" +
                "            color: #333333;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-size: 16px;\n" +
                "            color: #666666;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            text-decoration: none;\n" +
                "            padding: 10px 20px;\n" +
                "            border-radius: 3px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Welcome to Ride Share!</h1>\n" +
                "        <p>Dear " + username +",</p>\n" +
                "        <p>Thank you for joining Ride Share, the leading ride-sharing app that offers convenient and affordable transportation options.</p>\n" +
                "        <p>With Ride Share, you can enjoy the following services:</p>\n" +
                "        <ul>\n" +
                "            <li>Quick and easy ride-sharing with our reliable drivers</li>\n" +
                "            <li>Flexible renting options for your transportation needs</li>\n" +
                "            <li>Safe and secure transactions</li>\n" +
                "            <li>24/7 customer support</li>\n" +
                "        </ul>\n" +
                "        <p>We're excited to have you on board! Start using Ride Share today to experience hassle-free transportation at your fingertips.</p>\n" +
                "        <p>Download our app and get started:</p>\n" +
                "        <a href=\"https://www.example.com/app\" class=\"btn\">Download Now</a>\n" +
                "        <br><br>\n" +
                "        <p>If you have any questions or need assistance, our dedicated support team is here to help. Feel free to reach out to us at support@rideshare.com.</p>\n" +
                "        <p>Thank you once again for choosing Ride Share. We look forward to serving you!</p>\n" +
                "        <p>Best regards,<br>The Ride Share Team</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        return body;
    }
    private static String getSupportTemplate(String email, String name, String mobileNumber, String message) {

        var body ="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>User Support</title>\n" +
                "    <style>\n" +
                "        /* CSS Styles */\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f5f5f5;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            font-size: 24px;\n" +
                "            color: #333333;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-size: 16px;\n" +
                "            color: #666666;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            text-decoration: none;\n" +
                "            padding: 10px 20px;\n" +
                "            border-radius: 3px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>User details:</h1>\n" +
                "        <p>email: " + email +",</p>\n" +
                  "        <p>name: " + name +",</p>\n" +
                  "        <p>number: " + mobileNumber +",</p>\n" +

                "        <div> " +message + "</div>       "+
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        return body;
    }




}
