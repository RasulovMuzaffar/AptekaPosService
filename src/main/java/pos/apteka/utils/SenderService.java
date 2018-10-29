package pos.apteka.utils;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pos.apteka.model.Client;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SenderService {
    final static String URL_TELEGA = "https://api.telegram.org/";
    final static String TOKEN = "bot685836147:AAHQUsNeBoEDfX77ZXcK7N0QaNOqry5OFN4";
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

    //    public void htmlSend(String subject, String text, String to) throws MailException, MessagingException {
    public void htmlSend(String subject, String text, Client client) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
//        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setTo(client.getEmail());
        mimeMessageHelper.setSubject(subject);
        mimeMessage.setText(getFormattedMsg(text, client), null, "html");
        javaMailSender.send(mimeMessage);
    }

    private String getFormattedMsg(String text, Client client) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>").append("<body>");
//        здравствуйте, уважаемый Иван Иванов!
//Ваш ключ для продления подписки:
        sb.append("<p>").append("Здравствуйте, уважаемый менеджер аптеки \"" + client.getName() + "\"!").append("</p>");
        sb.append("<p>").append("Ваш ключ для продления подписки: ");
        sb.append("<br/><b>").append(text).append("</b>").append("</p>");
        sb.append("<p>").append("Cпасибо что выбирали нас!").append("</p>");
        sb.append("<p>").append("По дополнительным справкам обратитесь по телефону +998998899889").append("</p>");
        sb.append("<p>").append("С уважением ShamsWEB!").append("</p>");
        sb.append("</body>").append("</html>");

        return sb.toString();
    }

    public void sendMessageToTelegram(Client client, String hash) {

        OkHttpClient httpClient = new OkHttpClient();
//https://api.telegram.org/bot<ТОКЕН>/sendMessage?chat_id=<ID_ЧАТА>&text=Hello%20World
        HttpUrl.Builder httpBuider = HttpUrl.parse(URL_TELEGA + TOKEN + "/sendMessage").newBuilder();
        httpBuider.addQueryParameter("chat_id", client.getChatId())
                .addQueryParameter("parse_mode","markdown")
                .addQueryParameter("text", " *Ваш серийний номер для активации продукта aptekaPOS:* \r\n" + hash);
        String url = httpBuider.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
