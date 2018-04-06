import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

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

//            criteria
            session.beginTransaction();

            Query query = session.getNamedQuery("findStockByStockCode")
                    .setParameter("stockCode", "7052");
            query.list().forEach(System.out::println);

            System.out.println("------------------------");

            Query query1 = session.getNamedQuery("findStockByStockCodeNativeSQL")
                    .setParameter("stockCode", "7052");
            query1.list().forEach(System.out::println);

            System.out.println("------------------------");
            Query query2 = session.getNamedQuery("findStockByStockCodeNativeSQL3")
                    .setParameter("stockCode", "7052");
            query2.list().forEach(System.out::println);

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
