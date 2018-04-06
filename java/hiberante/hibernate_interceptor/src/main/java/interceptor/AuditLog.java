package interceptor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auditlog", catalog = "hibernate")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDIT_LOG_ID", nullable = false, unique = true)
    private Long auditLogId;

    @Column(name = "ACTION", nullable = false)
    private String action;

    @Column(name = "DETAIL", nullable = false)
    private String detail;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "ENTITY_ID", nullable = false)
    private long entityId;

    @Column(name = "ENTITY_NAME", nullable = false)
    private String entityName;

    public AuditLog(String action, String detail, Date createdDate, long entityId, String entityName) {
        this.action = action;
        this.detail = detail;
        this.createdDate = createdDate;
        this.entityId = entityId;
        this.entityName = entityName;
    }

    public Long getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(Long auditLogId) {
        this.auditLogId = auditLogId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "auditLogId=" + auditLogId +
                ", action='" + action + '\'' +
                ", detail='" + detail + '\'' +
                ", createdDate=" + createdDate +
                ", entityId=" + entityId +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
