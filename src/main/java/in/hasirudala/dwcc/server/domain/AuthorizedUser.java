package in.hasirudala.dwcc.server.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Audited
@Table(name = "authorized_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "uuid"})})
public class AuthorizedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @Column(name = "is_faux_deleted")
    private Boolean isFauxDeleted;

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    @NotAudited
    private UUID uuid;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Boolean isAdmin() { return isAdmin; }

    public void setAdmin(Boolean admin) { isAdmin = admin; }

    public Boolean isDisabled() { return isDisabled; }

    public void setDisabled(Boolean disabled) { isDisabled = disabled; }

    public Boolean isFauxDeleted() { return isFauxDeleted; }

    public void setFauxDeleted(Boolean fauxDeleted) { isFauxDeleted = fauxDeleted; }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }
}
