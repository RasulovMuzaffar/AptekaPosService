package pos.apteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pos.apteka.model.Client;
import pos.apteka.model.Extension;
import pos.apteka.repository.ClientJpaRepository;
import pos.apteka.repository.ExtJpaRepository;
import pos.apteka.services.ClientService;
import pos.apteka.utils.EncriptionService;
import pos.apteka.utils.SenderService;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
//    @Autowired
//    private ClientJpaRepository clientJpaRepository;
//
//    @Autowired
//    private ExtJpaRepository extJpaRepository;
//
//    @Autowired
//    private EncriptionService encriptionService;
//
//    @Autowired
//    private SenderService senderService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/hello")
    public String getHello() {
        return clientService.seyHello();
    }


    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/ext")
    public List<Extension> getClientExts(@RequestParam(value = "p") Long id) {
        return clientService.getClientExts(id);
    }

    @Transactional
    @PostMapping("/add")
    public Extension addNewClient(@RequestParam(value = "org") String name,
                                  @RequestParam(value = "phone") String phone,
                                  @RequestParam(value = "email") String email) {
        return clientService.addNewClient(name, phone, email);
    }

    @GetMapping("/addbotuser")
    public Client addBotUser(@RequestParam(value = "phone") String phone,
                             @RequestParam(value = "chatId") String chatId) {
        return clientService.addBotUser(phone, chatId);
    }
//
//    @GetMapping("/all")
//    public List<Client> getAllClients() {
//        System.out.println("---------------------------------------------");
//        return clientJpaRepository.findAll();
//    }
//
//    @GetMapping("/ext")
//    public List<Extension> getClientExts(@RequestParam(value = "p") Long id) {
//        return extJpaRepository.findAllByClientId(id);
//    }
//
//    @Transactional
//    @PostMapping("/add")
//    public Extension addNewClient(@RequestParam(value = "org") String name,
//                                  @RequestParam(value = "phone") String phone,
//                                  @RequestParam(value = "email") String email) {
//
//        Client client = new Client();
//        client.setName(name);
//        client.setEmail(email);
//        client.setPhone(phone);
//        //org.getName() + "|" + org.getEmail() + "|" + org.getPhone()
//        client.setHexc(encriptionService.getMD(name + "|" + email + "|" + phone));
//        clientJpaRepository.save(client);
//
//        Extension ext = new Extension();
//        ext.setClient(client);
//        ext.setDays(30);
//        extJpaRepository.save(ext);
//
//        if (ext != null) {
//            String str = encriptionService.getModificationSHA512(client, extJpaRepository.findFirstByOrderById());
//            System.out.println("==== " + str);
//
//            try {
////                senderService.htmlSend("HEX", str, client.getEmail());
//                senderService.htmlSend("HEX", str, client);
//                senderService.sendMessageToTelegram(client, str);
//                ext.setFlag(1);
//                ext.setHexSha(str);
//                extJpaRepository.save(ext);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//        return ext;
////        return clientJpaRepository.findById(1l);
//    }
//
//    @GetMapping("/addbotuser")
//    public Client addBotUser(@RequestParam(value = "phone") String phone,
//                             @RequestParam(value = "chatId") String chatId) {
//        Client client = clientJpaRepository.findClientByPhone("+" + phone);
//        if (client != null) {
//            client.setChatId(chatId);
//
//            clientJpaRepository.save(client);
//
//            return client;
//        } else {
//            return null;
//        }
//    }
}
