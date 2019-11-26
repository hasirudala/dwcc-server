package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    @NotAudited
    private UUID uuid;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public void assignUuid() { this.uuid = UUID.randomUUID(); }
}
