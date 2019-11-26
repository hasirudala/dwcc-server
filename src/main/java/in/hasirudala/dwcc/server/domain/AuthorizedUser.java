package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Audited
@Table(name = "authorized_users")
public class AuthorizedUser extends BaseEntity {
    @Column(name = "email", updatable = false, unique = true, nullable = false)
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @JsonProperty("isAdmin")
    public Boolean isAdmin() { return isAdmin; }

    public void setAdmin(Boolean admin) { this.isAdmin = admin; }
}
