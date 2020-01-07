package in.hasirudala.dwcc.server.web.contract;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class WasteBuyerRequest extends BaseRequest {
    private String name;
    private Long regionId;
    private Long zoneId;
    private String address;
    private Boolean isKspcbAuthorized;
    private Set<Long> acceptedItemIds = new HashSet<>();

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

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("isKspcbAuthorized")
    public Boolean getKspcbAuthorized() {
        return isKspcbAuthorized;
    }

    public void setKspcbAuthorized(Boolean kspcbAuthorized) {
        isKspcbAuthorized = kspcbAuthorized;
    }

    public Set<Long> getAcceptedItemIds() {
        return acceptedItemIds;
    }

    public void setAcceptedItemIds(Set<Long> acceptedItemIds) {
        this.acceptedItemIds = acceptedItemIds;
    }
}
