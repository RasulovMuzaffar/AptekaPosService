package pos.apteka.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SenderService {
    private JavaMailSender javaMailSender;

    @Autowired
    public SenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

//    public void sendNotification(Client client) throws MailException {
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(client.getEmail());
//        mail.setFrom("rasulovmuzaffar@gmail.com");
//        mail.setSubject("your serial key");
//        mail.setText("qweqweqweqwe");
//
//        javaMailSender.simpleSend(mail);
//    }


    public void simpleSend(String subject, String text, String to) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
//        mail.setFrom("rasulovmuzaffar@gmail.com");
        mail.setSubject(subject);
        mail.setText(text);

        javaMailSender.send(mail);
    }

    public void htmlSend(String subject, String text, String to) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessage.setText(getFormattedMsg(text),null, "html");
        javaMailSender.send(mimeMessage);
    }

    private String getFormattedMsg(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>").append("<body>");
//        здравствуйте, уважаемый Иван Иванов!
//Ваш ключ для продления подписки:
        sb.append("<p>").append("Здравствуйте, уважаемый Иван Иванов!").append("</p>");
        sb.append("<p>").append("Ваш ключ для продления подписки: ");
        sb.append("<b>").append(text).append("</b>").append("</p>");
        sb.append("<p>").append("Cпасибо что выбирали нас!").append("</p>");
        sb.append("<p>").append("По дополнительным справкам обратитесь по телефону +998998899889").append("</p>");
        sb.append("<p>").append("С уважением ShamsWEB!").append("</p>");
        sb.append("</body>").append("</html>");

        return sb.toString();
    }
}
