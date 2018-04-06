package other;

import interceptor.AuditLogInterceptor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("Hibernate one to many (Annotation)");
            AuditLogInterceptor interceptor = new AuditLogInterceptor();

            Session session = HibernateUtil.getSessionFactory().withOptions()
                    .interceptor(interceptor).openSession();



//            create stock with 10 daily records
            Transaction tx = session.beginTransaction();
            Stock stock = create( 10);
            session.save(stock);
            session.getTransaction().commit();


            //test insert
            tx = session.beginTransaction();
            Stock stockInsert = new Stock();
            stockInsert.setStockCode("1111");
            stockInsert.setStockName("hibernate");
            session.saveOrUpdate(stockInsert);
            tx.commit();

            //test update
            tx = session.beginTransaction();
            Query<Stock> query = session.createQuery("from Stock where stockCode = '1111'", Stock.class);
            Stock stockUpdate = query.list().get(0);
            stockUpdate.setStockName("hibernate-update");
            session.saveOrUpdate(stockUpdate);
            tx.commit();

            //test delete
            tx = session.beginTransaction();
            session.delete(stockUpdate);
            tx.commit();

            session.close();
            System.out.println("Done");
        } finally {
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    private static final Stock create(int amount) {
        final Stock stock = new Stock("code_f", "name_f");
        Set<StockDailyRecord> records = new HashSet<>(amount*2);
        for (int i = 0; i < amount; i ++) {
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
