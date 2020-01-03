package in.hasirudala.dwcc.server.web.contract;


public class ZoneRequest extends BaseRequest {
    private String name;
    private Long regionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}
