package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.BaseEntity;
import in.hasirudala.dwcc.server.web.contract.BaseRequest;

import java.util.List;

public interface EntryService<T1 extends BaseRequest, T2 extends BaseEntity, T3 extends BaseEntity> {
    void createAndAdd(T3 record, List<T1> entryPayloads);
    T2 create(T1 payload);
    void setAttributes(T2 entry, T1 payload);
    void update(T3 record, List<T1> entryPayloads);
}
