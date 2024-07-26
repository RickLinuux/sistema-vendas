package servicos;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;

public class ServicoEmail {

    public void servicoEmail(String email) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("seu@email.com", "suasenha");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("seu@email.com"));
                                                            //destinatario
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Seu Relatório PDF");


            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Prezado(a), \n\n Segue o anexo do histórico de compras realizando na empresa! Tenha cautela com " +
                    "o arquivo, pois contém dados sensíveis! Atenciosamente, Ghost.");


            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(new File("Relatório.pdf"));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
