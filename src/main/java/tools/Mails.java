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
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Loïc
 */
public class Mails {

    public static void printLine(String s) {
        System.out.println("////////////////////////////////////////////////////////////");
        System.out.println("\n============================================================");
        System.out.println("                    " + s + "                                 ");
        System.out.println("------------------------------------------------------------");
    }

    public static void sendEmail() {
        final String username = "lcrusson.pro@gmail.com";
        final String password = "richegrospenis";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", true);

        System.out.println(" 1 ");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        System.out.println(" 2 ");
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lcrusson.pro@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("loic.crusson@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmail2() {
        String to = "lcrusson.pro@gmail.com";
        String from = "loic.crusson@gmail.com";

        String host = "smtp.gmail.com";

        String pwd = "richegrospenis";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);

        // Setup your mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pwd);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.EnableSSL.enable", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        //Session session = Session.getDefaultInstance(properties);
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "lcrusson.pro@gmail.com", "richegrospenis");
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("This is the Subject Line!");
            message.setText("This is actual message");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendEmail3() {

        final String username = "lcrusson.pro@gmail.com";
        final String password = "richegrospenis";

        Properties props = new Properties();
        // tls
        props.put("mail.smtp.host", "smtp.gmail.com"); // localhost
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("username", "password");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lcrusson.pro@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("loic.crusson@live.fr"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmail4() throws MessagingException {

        final String username = "lcrusson.pro@gmail.com";
        final String password = "richegrospenis";
        String subject = "kekette";
        String body = "Dans ton cul";

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.from", username);
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
        mailProps.put("mail.smtp.port", "587");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.socketFactory.port", "587");
        mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.put("mail.smtp.socketFactory.fallback", "true"); // false
        mailProps.put("mail.smtp.starttls.enable", "true");

        System.setProperty("javax.net.ssl.trustStore", "clientTrustStore.key"); // not found 
        System.setProperty("javax.net.ssl.trustStorePassword", "qwerty");

        Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(username));

        //Getting  FROM_MAIL
        String[] emails = new String[1];
        emails[0] = new String();
        emails[0] = "loic.crusson@live.fr";

//        String[] emails = { recipients };
        InternetAddress dests[] = new InternetAddress[emails.length];
        for (int i = 0; i < emails.length; i++) {
            dests[i] = new InternetAddress(emails[i].trim().toLowerCase());
        }
        message.setRecipients(Message.RecipientType.TO, dests);
        message.setSubject(subject, "UTF-8");
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(body, "text/html;charset=utf-8");
        mp.addBodyPart(mbp);
        message.setContent(mp);
        message.setSentDate(new java.util.Date());

        Transport.send(message);

    }
}
