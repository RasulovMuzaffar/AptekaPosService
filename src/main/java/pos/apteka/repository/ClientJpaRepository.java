package pos.apteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.apteka.model.Client;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {

//    Client findByHexc(String hex);
//
    Client findClientByHexc(String hex);
}
