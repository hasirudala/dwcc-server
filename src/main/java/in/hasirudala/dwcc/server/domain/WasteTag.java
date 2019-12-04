package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "waste_tags")
public class WasteTag extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_type_id")
    private WasteType type;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<WasteItem> items = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WasteType getType() {
        return type;
    }

    public void setType(WasteType type) {
        this.type = type;
    }

    public Long getTypeId() {
        if (type == null) return null;
        return type.getId();
    }

    public Set<WasteItem> getItems() {
        return items;
    }

    public void setItems(Set<WasteItem> items) {
        this.items = items;
    }
}
