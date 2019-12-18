package in.hasirudala.dwcc.server.web.contract;


public class WasteTagRequest extends BaseRequest {
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

    public void setTypeId(Long wasteTypeId) {
        this.typeId = wasteTypeId;
    }
}
