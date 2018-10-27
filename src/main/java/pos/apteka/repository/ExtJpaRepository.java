package pos.apteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pos.apteka.model.Client;
import pos.apteka.model.Extension;

import java.util.List;

public interface ExtJpaRepository extends JpaRepository<Extension, Long> {

    @Query(value = "SELECT * FROM extension ext " +
            "INNER JOIN clients cl ON ext.id_client = cl.id " +
            "WHERE ext.flag = 0 and ext.status = 0 and ext.id_client = ?1 " +
            "ORDER BY ext.id DESC", nativeQuery = true)
    Extension findExtByClientAndFlag0AndStatus0(Long idClient);

    @Query(value = "SELECT * FROM extension ext " +
            "INNER JOIN clients cl ON ext.id_client = cl.id " +
            "WHERE ext.flag = 1 and ext.status = 0 and ext.id_client = ?1 " +
            "ORDER BY ext.id DESC", nativeQuery = true)
    Extension findExtByClientAndFlag1AndStatus0(Long idClient);

    List<Extension> findAllByClientId(Long idClient);

    Extension findFirstByOrderById();
}
