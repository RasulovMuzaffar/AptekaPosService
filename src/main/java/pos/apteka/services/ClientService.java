package pos.apteka.services;

import org.springframework.stereotype.Service;
import pos.apteka.model.Client;
import pos.apteka.model.Extension;

import java.util.List;

@Service
public interface ClientService {
    String seyHello();

    List<Client> getAllClients();

    List<Extension> getClientExts(Long id);

    Extension addNewClient(String name, String phone, String email);

    Client addBotUser(String phone, String chatId);
}
