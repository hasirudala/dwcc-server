package in.hasirudala.dwcc.server.web.contract;

import java.util.HashSet;
import java.util.Set;

public class ExpenseItemRequest extends BaseRequest {
    private String name;
    private Long typeId = null;
    private Set<Long> tagIds = new HashSet<>();
    private Boolean askNumberOfUnits;
    private String unitLabel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Boolean getAskNumberOfUnits() {
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
