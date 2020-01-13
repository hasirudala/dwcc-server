package in.hasirudala.dwcc.server.web.contract;


public class ExpenseTagRequest extends BaseRequest {
    private String name;
    private Long typeId = null;

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
}
