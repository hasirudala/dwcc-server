package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "expense_items")
public class ExpenseItem extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ExpenseType type = null;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "expense_items_tags",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<ExpenseTag> tags = new HashSet<>();

    @Column(name = "ask_num_units")
    private Boolean askNumberOfUnits;

    @Column
    private String unitLabel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public Long getTypeId() {
        if (type == null) return null;
        return type.getId();
    }

    public Set<ExpenseTag> getTags() {
        return tags;
    }

    public void setTags(Set<ExpenseTag> tags) {
        this.tags = tags;
    }

    public Set<Long> getTagIds() {
        return tags.stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }

    @JsonProperty("askNumberOfUnits")
    public Boolean askNumberOfUnits() {
        return askNumberOfUnits;
    }

    public void setAskNumberOfUnits(Boolean askNumberOfUnits) {
        this.askNumberOfUnits = askNumberOfUnits;
    }

    public String getUnitLabel() {
        return unitLabel;
    }

    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }
}
