import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class App2 {
    public static void main(String[] args) {
        try {
            System.out.println("Hibernate one to many (Annotation)");
            Session session = HibernateUtil.getSessionFactory().openSession();

//          criteria
            session.beginTransaction();

            Date start = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(LocalDate.now().plusDays(4).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Long volume = 100L;
            System.out.println("\n----------- getStockDailyRecordCriteria -----------");
            System.out.println(start);
            System.out.println(end);
            System.out.println(volume);
            getStockDailyRecordCriteria(start, end, volume,session).forEach(System.out::println);


            System.out.println("\n----------- getStockDailyRecordCriteriaPaginated -----------");
            getStockDailyRecordCriteriaPaginated(volume, session).forEach(System.out::println);
            System.out.println("\n----------- -----------");

            System.out.println("\n----------- -----------");

            session.getTransaction().commit();

            System.out.println("Done");
        } finally {
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    public static List<StockDailyRecord> getStockDailyRecordCriteria(Date startDate, Date endDate,
                                                   Long volume, Session session){

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StockDailyRecord> criteria = builder.createQuery(StockDailyRecord.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        criteria.select(root);
        List<Predicate> predicates = new LinkedList<>();
        Predicate[] predicatesArr = {};

        if(startDate!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get(StockDailyRecord_.date), startDate));
        }
        if(endDate!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get(StockDailyRecord_.date), endDate));
        }
        if(volume!=null){
            predicates.add(builder.ge(root.get(StockDailyRecord_.volume), volume));
        }

        predicatesArr = predicates.toArray(predicatesArr);
        criteria.where(predicatesArr);

        criteria.orderBy(builder.desc(root.get(StockDailyRecord_.date)));

        return session.createQuery(criteria).list();
    }

    public static List<StockDailyRecord> getStockDailyRecordCriteriaPaginated(Long volume, Session session) {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StockDailyRecord> criteria = builder.createQuery(StockDailyRecord.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        criteria.select(root);
        criteria.where(builder.ge(root.get(StockDailyRecord_.volume), volume));
        criteria.orderBy(builder.asc(root.get(StockDailyRecord_.date)));

        return session.createQuery(criteria).setMaxResults(3).setFirstResult(2).list();
    }
}