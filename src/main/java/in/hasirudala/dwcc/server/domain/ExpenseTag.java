package in.hasirudala.dwcc.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "expense_tags")
public class ExpenseTag extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ExpenseType type;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<ExpenseItem> items = new HashSet<>();

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

    public Set<ExpenseItem> getItems() {
        return items;
    }

    public void setItems(Set<ExpenseItem> items) {
        this.items = items;
    }
}
