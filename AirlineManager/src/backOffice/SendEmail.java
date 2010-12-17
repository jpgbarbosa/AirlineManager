package backOffice;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class responsible for sending Emails to Clients.
 * 
 * @author Daniela Fontes, Ivo Correia, Jo‹o Penetra, Jo‹o Barbosa, Ricardo
 *         Bernardino
 * 
 */
public class SendEmail {
	/**
	 * This is the function used to send emails to the clients of our
	 * application.
	 * 
	 * @param smtpHost
	 *            SMTP server host name
	 * @param smtpPort
	 *            SMTP server port
	 * @param from
	 *            Sender's email
	 * @param to
	 *            Destination's email
	 * @param subject
	 *            Subject of the Email
	 * @param content
	 *            Content of the Email
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void send(String smtpHost, int smtpPort, String from,
			String to, String subject, String content) throws AddressException,
			MessagingException {

		// Create a mail session
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(false);

		// Construct the message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setText(content);

		// Send the message
		Transport.send(msg);
	}

	public static void main(String[] args) throws Exception {
		// Send a test message
		send("smtp.sapo.pt", 25, "airlinemanager@lalala.com",
				"icorreia@student.dei.uc.pt", "Hello",
				"Hello, \n\n How are you ?");
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	static private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = "airplanemanager@sapo.pt";
			String password = "1olaadeus";
			return new PasswordAuthentication(username, password);
		}
	}
}
