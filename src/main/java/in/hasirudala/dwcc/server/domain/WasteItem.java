package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "waste_items")
public class WasteItem extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_type_id")
    private WasteType type = null;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "waste_items_waste_tags",
            joinColumns = {@JoinColumn(name = "waste_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "waste_tag_id")})
    private Set<WasteTag> tags = new HashSet<>();

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

    public Set<WasteTag> getTags() {
        return tags;
    }

    public void setTags(Set<WasteTag> tags) {
        this.tags = tags;
    }

    public Set<Long> getTagIds() {
        return tags.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }
}
