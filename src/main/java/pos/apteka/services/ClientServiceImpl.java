package pos.apteka.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pos.apteka.model.Client;
import pos.apteka.model.Extension;
import pos.apteka.repository.ClientJpaRepository;
import pos.apteka.repository.ExtJpaRepository;
import pos.apteka.utils.EncriptionService;
import pos.apteka.utils.SenderService;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientJpaRepository clientJpaRepository;

    @Autowired
    private ExtJpaRepository extJpaRepository;

    @Autowired
    private EncriptionService encriptionService;

    @Autowired
    private SenderService senderService;

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    public String seyHello() {
        return "hello!";
    }

    public List<Client> getAllClients() {
        System.out.println("---------------------------------------------");
        return clientJpaRepository.findAll();
    }

    public List<Extension> getClientExts(Long id) {
        return extJpaRepository.findAllByClientId(id);
    }

    @Transactional
    public Extension addNewClient(String name, String phone, String email) {

        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        //org.getName() + "|" + org.getEmail() + "|" + org.getPhone()
        client.setHexc(encriptionService.getMD(name + "|" + email + "|" + phone));
        clientJpaRepository.save(client);

        Extension ext = new Extension();
        ext.setClient(client);
        ext.setDays(30);
        extJpaRepository.save(ext);

        if (ext != null) {
            String str = encriptionService.getModificationSHA512(client, extJpaRepository.findFirstByOrderById());
            System.out.println("==== " + str);

            try {
//                senderService.htmlSend("HEX", str, client.getEmail());
                senderService.htmlSend("HEX", str, client);
                senderService.sendMessageToTelegram(client, str);
                ext.setFlag(1);
                ext.setHexSha(str);
                extJpaRepository.save(ext);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return ext;
//        return clientJpaRepository.findById(1l);
    }

    public Client addBotUser(String phone, String chatId) {
        Client client = clientJpaRepository.findClientByPhone("+" + phone);
        if (client != null) {
            client.setChatId(chatId);

            clientJpaRepository.save(client);

            return client;
        } else {
            return null;
        }
    }
}
