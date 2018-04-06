import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class App1 {
    public static void main(String[] args) {
        try {
            System.out.println("Hibernate one to many (Annotation)");
            Session session = HibernateUtil.getSessionFactory().openSession();

//          criteria
            session.beginTransaction();

            System.out.println("\n----------- selectEntity -----------");
            System.out.println(selectEntity(session));

            System.out.println("\n----------- selectSinglePropertyFromEntity -----------");
            System.out.println(selectSinglePropertyFromEntity(session));

            System.out.println("\n----------- selectMultiplePropertiesFromEntityAsArray -----------");
            List<Object[]> data = selectMultiplePropertiesFromEntityAsArray(session);
            System.out.println(data);
            System.out.println(data.get(0));
            System.out.println(data.get(0)[0]);
            System.out.println(data.get(0)[1]);

            System.out.println("\n----------- selectMultiplePropertiesFromEntityAsMultiSelect -----------");
            List<Object[]> data1 = selectMultiplePropertiesFromEntityAsMultiSelect(session);
            System.out.println(data1);
            System.out.println(data1.get(0));
            System.out.println(data1.get(0)[0]);
            System.out.println(data1.get(0)[1]);

            System.out.println("\n----------- selectMultiplePropertiesFromEntityAsWrapper -----------");
            List<Wrapper> data2 = selectMultiplePropertiesFromEntityAsWrapper(session);
            System.out.println(data2);
            System.out.println(data2.get(0));
            System.out.println(data2.get(0).getRecordId());
            System.out.println(data2.get(0).getDate());

            System.out.println("\n----------- selectMultiplePropertiesFromEntityAsTuple -----------");
            selectMultiplePropertiesFromEntityAsTuple(session);

            System.out.println("\n----------- selectEntityByParameter -----------");
            System.out.println(selectEntityByParameter(session));

            System.out.println("\n----------- selectWithGroupBy -----------");
            selectWithGroupBy(session);

            System.out.println("\n----------- -----------");

            System.out.println("\n----------- -----------");

            System.out.println("\n----------- -----------");

            session.getTransaction().commit();

            System.out.println("Done");
        } finally {
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    public static List<StockDailyRecord> selectEntity(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StockDailyRecord> criteria = builder.createQuery(StockDailyRecord.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), 100L));

        return session.createQuery(criteria).getResultList();
    }

    public static List<Integer> selectSinglePropertyFromEntity(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        criteria.select(root.get(StockDailyRecord_.recordId));
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), 100L));

        return session.createQuery(criteria).getResultList();
    }

    public static List<Object[]> selectMultiplePropertiesFromEntityAsArray(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);
        Path<Integer> recordId = root.get(StockDailyRecord_.recordId);
        Path<Date> date = root.get(StockDailyRecord_.date);

        criteria.select(builder.array(recordId, date));
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), 100L));

        return session.createQuery(criteria).getResultList();
    }


    public static List<Object[]> selectMultiplePropertiesFromEntityAsMultiSelect(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);
        Path<Integer> recordId = root.get(StockDailyRecord_.recordId);
        Path<Date> date = root.get(StockDailyRecord_.date);

        criteria.multiselect(recordId, date);
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), 100L));

        return session.createQuery(criteria).getResultList();
    }

    public static List<Wrapper> selectMultiplePropertiesFromEntityAsWrapper(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Wrapper> criteria = builder.createQuery(Wrapper.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        Path<Integer> recordId = root.get(StockDailyRecord_.recordId);
        Path<Date> date = root.get(StockDailyRecord_.date);

        criteria.select(builder.construct(Wrapper.class, recordId, date));
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), 100L));

        return session.createQuery(criteria).getResultList();
    }


    public static List<Tuple> selectMultiplePropertiesFromEntityAsTuple(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        Path<Integer> recordId = root.get(StockDailyRecord_.recordId);
        Path<Date> date = root.get(StockDailyRecord_.date);

        criteria.multiselect(recordId, date);
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), 100L));

        List<Tuple> results = session.createQuery(criteria).getResultList();

        System.out.println(results);
        System.out.println(results.get(0));
        System.out.println(results.get(0).get(recordId));
        System.out.println(results.get(0).get(date));

        System.out.println(results.get(0).get(0));
        System.out.println(results.get(0).get(1));
        return results;
    }

    public static List<StockDailyRecord> selectEntityByParameter(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StockDailyRecord> criteria = builder.createQuery(StockDailyRecord.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        ParameterExpression<Long> volume = builder.parameter( Long.class );

        criteria.select(root);
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), volume));

        return session.createQuery(criteria)
                .setParameter(volume, 100L)
                .getResultList();
    }

    public static List<Tuple> selectWithGroupBy(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<StockDailyRecord> root = criteria.from(StockDailyRecord.class);

        ParameterExpression<Long> volume = builder.parameter( Long.class );

        criteria.groupBy(root.get(StockDailyRecord_.volume));
        criteria.multiselect(root.get(StockDailyRecord_.volume), builder.count(root));
        criteria.where(builder.equal(root.get(StockDailyRecord_.volume), volume));

        List<Tuple> results = session.createQuery(criteria)
                .setParameter(volume, 100L)
                .getResultList();

        System.out.println(results);
        System.out.println(results.get(0));

        System.out.println(results.get(0).get(0));
        System.out.println(results.get(0).get(1));

        return results;
    }





//    public static List<StockDailyRecord> getStockDailyRecordCriteria(Date startDate, Date endDate,
//                                                   Long volume, Session session){
//
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<StockDailyRecord> criteriaQuery = builder.createQuery(StockDailyRecord.class);
//        Root<StockDailyRecord> root = criteriaQuery.from(StockDailyRecord.class);
//
//        criteriaQuery.select(root);
//
//        if(startDate!=null){
//            criteriaQuery = criteriaQuery.where();
//            criteria.add(Expression.ge("date",startDate));
//        }
//        if(endDate!=null){
//            criteria.add(Expression.le("date",endDate));
//        }
//        if(volume!=null){
//            criteria.add(Expression.ge("volume",volume));
//        }
//        criteria.addOrder(Order.asc("date"));
//
//        return criteria.list();
//    }
}

class Wrapper {
    private final Integer recordId;
    private final Date date;

    public Wrapper(Integer recordId, Date date) {
        this.recordId = recordId;
        this.date = date;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wrapper wrapper = (Wrapper) o;
        return Objects.equals(recordId, wrapper.recordId) &&
                Objects.equals(date, wrapper.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(recordId, date);
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "recordId=" + recordId +
                ", date=" + date +
                '}';
    }
}
