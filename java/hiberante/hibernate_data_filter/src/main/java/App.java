import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("Hibernate one to many (Annotation)");
            Session session = HibernateUtil.getSessionFactory().openSession();

//            create stock with 10 daily records
//            session.beginTransaction();
//            Stock stock = create( 10);
//            session.save(stock);
//            session.getTransaction().commit();

            System.out.println("****** Enabled Filter ******");
            Filter filter = session.enableFilter("stockRecordFilter");
            Filter filter1 = session.enableFilter("stockRecordFilter1");

            filter.setParameter("stockRecordFilterParam", Date.from(LocalDate.now()
                    .plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            filter1.setParameter("stockRecordFilterParam1", Date.from(LocalDate.now()
                    .plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));

            session.beginTransaction();

            System.out.println("------ STOCK ------");
            session.createQuery("FROM Stock", Stock.class).list().forEach(stock -> {
                System.out.println(stock);
                stock.getStockDailyRecords().forEach(System.out::println);
            });
            System.out.println("------ STOCK1 ------");
            session.createQuery("FROM Stock1", Stock1.class).list().forEach(stock -> {
                System.out.println(stock);
                stock.getStockDailyRecords().forEach(System.out::println);
            });


            session.getTransaction().commit();
            session.close();

            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println("****** Disabled Filter ******");

            session.beginTransaction();
            System.out.println("------ STOCK ------");
            session.createQuery("FROM Stock", Stock.class).list().forEach(stock -> {
                System.out.println(stock);
                stock.getStockDailyRecords().forEach(System.out::println);
            });
            System.out.println("------ STOCK1 ------");
            session.createQuery("FROM Stock1", Stock1.class).list().forEach(stock -> {
                System.out.println(stock);
                stock.getStockDailyRecords().forEach(System.out::println);
            });

            session.getTransaction().commit();

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
