/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.server.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;
import java.util.logging.Logger;


public class Mailer implements IMail {
    private String fromAddress;
    private String smtpServer;

    @Inject
    public Mailer(@FromAddress
                  String fromAddress, @SMTPServer
    String smtpServer) {
        this.fromAddress = fromAddress;
        this.smtpServer = smtpServer;
    }

    @Override
    public void send(String recipient, String subject, String body) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtpServer);

        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromAddress));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        } catch (AddressException e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().info(e.getLocalizedMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().info(e.getLocalizedMessage());
        }
    }

    @Override
    public void send(String recipient, String subject, String body,
                     byte[] attachment) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtpServer);

        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromAddress));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipient));
            msg.setSubject(subject);

            // create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            //fill message
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();

            DataSource source = new ByteArrayDataSource(attachment, "image/jpeg");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("image.jpg");
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (AddressException e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().info(e.getLocalizedMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().info(e.getLocalizedMessage());
        }
    }
}
