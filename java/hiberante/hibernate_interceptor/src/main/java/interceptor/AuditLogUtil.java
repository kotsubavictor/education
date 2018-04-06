package interceptor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import other.HibernateUtil;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AuditLogUtil {
    public static void LogIt(String action, IAuditLog entity) {

        Session tempSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = tempSession.beginTransaction();

        try {

            AuditLog auditRecord = new AuditLog(action, entity.getLogDeatil(),
                    Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                    entity.getId(), entity.getClass().toString());
            tempSession.save(auditRecord);
            tempSession.flush();
            tx.commit();
        } finally {
            tempSession.close();
        }
    }
}
