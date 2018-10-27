package pos.apteka.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String hexc;

    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<Extension> ext = new HashSet<>();

    public Client() {
    }

    public Client(String name, String email, String phone, String hexc, Set<Extension> ext) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hexc = hexc;
        this.ext = ext;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHexc() {
        return hexc;
    }

    public void setHexc(String hexc) {
        this.hexc = hexc;
    }

    public Set<Extension> getExt() {
        return ext;
    }

    public void setExt(Set<Extension> ext) {
        this.ext = ext;
    }

}
