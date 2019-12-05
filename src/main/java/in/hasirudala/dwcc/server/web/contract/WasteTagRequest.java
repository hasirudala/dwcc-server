package in.hasirudala.dwcc.server.web.contract;


public class WasteTagRequest {
    private Long id;
    private String uuid;
    private String name;
    private Long typeId = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
