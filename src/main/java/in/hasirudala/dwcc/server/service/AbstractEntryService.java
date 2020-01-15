package in.hasirudala.dwcc.server.service;

import in.hasirudala.dwcc.server.domain.BaseEntity;
import in.hasirudala.dwcc.server.web.contract.BaseRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


abstract class AbstractEntryService<T1 extends BaseRequest,
                                                T2 extends BaseEntity,
                                                T3 extends BaseEntity> implements EntryService<T1, T2, T3> {

    void removeDeletedEntries(Set<T2> existingEntries, List<T1> entryPayloads) {
        List<Long> idsInEntryPayloads = entryPayloads
                                                .stream()
                                                .map(BaseRequest::getId)
                                                .collect(Collectors.toList());
        existingEntries
                .removeIf(entry -> !idsInEntryPayloads.contains(entry.getId()));
    }

    void updateExistingEntries(Set<T2> existingEntries, List<T1> entryPayloads) {
        entryPayloads
                .stream()
                .filter(item -> item.getId() != null)
                .forEach(updatedEntry ->
                                 existingEntries
                                         .stream()
                                         .filter(entry -> entry.getId().equals(updatedEntry.getId()))
                                         .findFirst()
                                         .ifPresent(existingEntry ->
                                                            setAttributes(existingEntry, updatedEntry)));
    }

    void addNewIncomingEntries(T3 record, List<T1> entryPayloads) {
        createAndAdd(
                record,
                entryPayloads
                        .stream()
                        .filter(item -> item.getId() == null)
                        .collect(Collectors.toList()));
    }
}
