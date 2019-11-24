package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Audited
@Table(name = "authorized_users")
public class AuthorizedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "email", updatable = false, unique = true, nullable = false)
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    @NotAudited
    private UUID uuid;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @JsonProperty("isAdmin")
    public Boolean isAdmin() { return isAdmin; }

    public void setAdmin(Boolean admin) { isAdmin = admin; }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public void assignUuid() { this.uuid = UUID.randomUUID(); }
}
