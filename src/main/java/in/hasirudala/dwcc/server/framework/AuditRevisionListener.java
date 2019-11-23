package in.hasirudala.dwcc.server.framework;

import in.hasirudala.dwcc.server.domain.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;

@Configurable
public class AuditRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity revision = (AuditRevisionEntity) revisionEntity;
        String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        revision.setUserId(emailId);
    }
}
