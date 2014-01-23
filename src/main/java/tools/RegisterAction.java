/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Loïc
 */
public class RegisterAction {

    public String execute() {

        RegisterAction mailBean = new RegisterAction();

        String subject = "Your username & password ";
        String username = "loic";
        String email = "loic.crusson@live.fr";
        String password = "richegrospenis";

        String message = "Hi," + username;
        message += "\n \n Your username is " + email;
        message += "\n \n Your password is " + password;
        message += "\n \n Please login to the web site with your username and password.";
        message += "\n \n Thanks";
        message += "\n \n \n Regards";

        //Getting  FROM_MAIL
        String[] recipients = new String[1];
        recipients[0] = new String();
        recipients[0] = "loic.crusson@live.fr";

        try {
            mailBean.sendMail(recipients, subject, message);

            return "success";
        } catch (Exception e) {
            System.out.println("Error in sending mail:" + e);
        }

        return "failure";
    }

    
    
    public void sendMail(String recipients[], String subject, String message)
            throws MessagingException {
        boolean debug = false;

        //Set the host smtp address
        Properties props = new Properties();

        // Setup your mail server
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // create some properties and get the default Session
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "lcrusson.pro@gmail.com", "richegrospenis");// Specify the Username and the PassWord
            }
        });
        
        
        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }

        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // Optional : You can also set your custom headers  in the Email if you Want
        //msg.addHeader("MyHeaderName", "myHeaderValue");
        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");

        //send message
        Transport.send(msg);

        System.out.println("Message Sent Successfully");
    }

}
