package backOffice;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
    
public class SendEmail {
	/**
	 * É esta a função utilizada para enviar Emails para os Clientes da nossa Aplicação.
	 * @param smtpHost Endereço do servidor de SMTP
	 * @param smtpPort Porta do servidor de SMTP
	 * @param from Endereço Email do remetente
	 * @param to Endereço Email do destinatário
	 * @param subject Assunto do Email
	 * @param content Conteúdo do Email
	 * @throws AddressException
	 * @throws MessagingException
	 */
    public static void send(String smtpHost, int smtpPort,
                            String from, String to,
                            String subject, String content)
                throws AddressException, MessagingException {

        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", ""+smtpPort);
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
        send("smtp.sapo.pt", 25, "airlinemanager@lalala.com", "icorreia@student.dei.uc.pt",
             "Hello", "Hello, \n\n How are you ?");
    }
    /**
     * SimpleAuthenticator is used to do simple authentication
     * when the SMTP server requires it.
     */
	static private class SMTPAuthenticator extends javax.mail.Authenticator
	{
	
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	    	String username = "airplanemanager@sapo.pt";
	        String password = "1olaadeus";
	        return new PasswordAuthentication(username, password);
	    }
	}
}



