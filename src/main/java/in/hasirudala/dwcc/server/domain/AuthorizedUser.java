package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Audited
@Table(name = "authorized_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "uuid"})})
public class AuthorizedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "email")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Column(name = "name")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @Column(name = "is_admin")
    @NotNull(message = "isAdmin cannot be null")
    private Boolean isAdmin;

    @Column(name = "is_disabled")
    @NotNull(message = "isDisabled cannot be null")
    private Boolean isDisabled = false;

    @Column(name = "is_faux_deleted")
    private Boolean isFauxDeleted = false;

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

    @JsonProperty("isDisabled")
    public Boolean isDisabled() { return isDisabled; }

    public void setDisabled(Boolean disabled) { isDisabled = disabled; }

    @JsonIgnore
    public Boolean isFauxDeleted() { return isFauxDeleted; }

    @JsonIgnore
    public void setFauxDeleted(Boolean fauxDeleted) { isFauxDeleted = fauxDeleted; }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public void assignUuid() { this.uuid = UUID.randomUUID(); }
}
