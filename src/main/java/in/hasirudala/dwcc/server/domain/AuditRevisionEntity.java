package in.hasirudala.dwcc.server.domain;

import in.hasirudala.dwcc.server.framework.AuditRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;

@Entity
@Table(name = "revinfo", schema = "audit")
@AttributeOverrides({
    @AttributeOverride(name="timestamp", column=@Column(name="revtstmp")),
    @AttributeOverride(name="id", column=@Column(name="rev"))})
@RevisionEntity(AuditRevisionListener.class)
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "user_id", nullable = false)
    private String userId;

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

}


