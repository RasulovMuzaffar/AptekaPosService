package pos.apteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pos.apteka.model.Client;
import pos.apteka.model.Extension;
import pos.apteka.repository.ClientJpaRepository;
import pos.apteka.repository.ExtJpaRepository;
import pos.apteka.utils.EncriptionService;
import pos.apteka.utils.SenderService;

import java.util.List;

@RestController
@RequestMapping("/ext")
public class ExtController {
    @Autowired
    private ExtJpaRepository extJpaRepository;
    @Autowired
    private ClientJpaRepository clientJpaRepository;

    @Autowired
    private SenderService senderService;

    @Autowired
    private EncriptionService encriptionService;

    @GetMapping("/all")
    public List<Extension> getAllExts() {
        return extJpaRepository.findAll();
    }

    @GetMapping("/client/{id}")
    public List<Extension> getAllExtsClient(@PathVariable Long id) {
        return extJpaRepository.findAllByClientId(id);
    }

    @GetMapping("/extension")
    public String getLicense(@RequestParam(value = "p") String hex) {
        Client client = clientJpaRepository.findClientByHexc(hex);
        Extension ext = extJpaRepository.findExtByClientAndFlag0AndStatus0(client.getId());
        if (client != null && ext != null) {
            String str = encriptionService.getModificationSHA512(client, ext);
            System.out.println("==== " + str);

//            try {
//                senderService.htmlSend("HEX", str, "rasulovmuzaffar@umail.uz");
////                senderService.htmlSend("HEX", str, "0999xc@mail.ru");
            ext.setFlag(1);
            ext.setHexSha(str);
            extJpaRepository.save(ext);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
            return "На Вашу почту было отправлено письмо с серийным номером!";
        } else {
            return "Обратитесь поставщику!";
        }
    }

    @GetMapping("/activate")
    public String setLicenseStatus(@RequestParam(value = "p") String hex) {
        Client client = clientJpaRepository.findClientByHexc(hex);
        Extension ext = extJpaRepository.findExtByClientAndFlag1AndStatus0(client.getId());
        if (client != null && ext != null) {
            ext.setStatus(1);
            extJpaRepository.save(ext);
            return "Ваша лицензия продлена на " + ext.getDays() + " дней.";
        } else {
            return null;
        }
    }
}
