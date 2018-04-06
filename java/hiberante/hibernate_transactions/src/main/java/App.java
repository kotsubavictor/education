import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        Session session = null;
        Transaction tx = null;
        try {
            System.out.println("Hibernate one to many (Annotation)");
            session = HibernateUtil.getSessionFactory().openSession();

//            create stock with 10 daily records
//            session.beginTransaction();
//            Stock stock = create( 10);
//            session.save(stock);
//            session.getTransaction().commit();

//            criteria
            tx = session.beginTransaction();
            tx.setTimeout(5);

            //doSomething(session);

//          In Hibernate, the transaction management is quite standard,
//          just remember any exceptions thrown by Hibernate are FATAL,
//          you have to roll back the transaction and close the current session
//          immediately.
            if(true) {
                throw new UnsupportedOperationException("Boom!");
            }

            tx.commit();
//            session.getTransaction().commit();


            System.out.println("Done");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    private static final Stock create(int amount) {
        final Stock stock = new Stock("code_f", "name_f");
        Set<StockDailyRecord> records = new HashSet<>(amount * 2);
        for (int i = 0; i < amount; i++) {
            StockDailyRecord dailyRecord = new StockDailyRecord();
            dailyRecord.setPriceChange(10.2F);
            dailyRecord.setPriceClose(10.3F);
            dailyRecord.setPriceOpen(10.4F);
            dailyRecord.setVolume(100L);
            dailyRecord.setDate(Date.from(LocalDate.now().plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dailyRecord.setStock(stock);
            records.add(dailyRecord);
        }
        stock.getStockDailyRecords().addAll(records);
        return stock;
    }
}
