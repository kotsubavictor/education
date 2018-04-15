import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate one to many (Annotation)");
        final Integer id = 34;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Stock stock = null;
            stock = session.get(Stock.class, 300);
            System.out.println("GET RETURNS NULL: Nonexisting object = " + stock);
            stock = session.load(Stock.class, 300);
            System.out.println("LOAD RETURNS a proxy without hiting a db: successfully for nonexisting object");
            try {
                stock.getStockDailyRecords();
            } catch (Throwable e) {
                System.out.println("But accessing proxy variables causes exception = "+ e);
            }

            // load an object immediately
            System.out.println("object via get");
            stock = session.get(Stock.class, 34);
            System.out.println("after");
            stock.getStockDailyRecords();
            System.out.println("after property");
            // does not load an object immediately
            System.out.println("object via load");
            stock = session.load(Stock.class, 35);
            System.out.println("after");
            stock.getStockDailyRecords();
            System.out.println("after property");

            // Proxy objects is usefull for references, because we do not load them
            System.out.println("Saving daily record without loading a stock object, but using proxy instead");
            stock = session.load(Stock.class, 36);
            StockDailyRecord record = createRecord(30);
            record.setStock(stock);
            session.saveOrUpdate(record);
            session.getTransaction().commit();

            System.out.println("Done");
        } finally {
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    private static final Stock create(int amount) {
        return create(amount, true);
    }

    private static final Stock create(int amount, boolean child) {
        final Stock stock = new Stock("code_f", "name_f");
        if (child) {
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
        }
        return stock;
    }

    private static final StockDailyRecord createRecord(int iDays) {
        StockDailyRecord dailyRecord = new StockDailyRecord();
        dailyRecord.setPriceChange(10.2F);
        dailyRecord.setPriceClose(10.3F);
        dailyRecord.setPriceOpen(10.4F);
        dailyRecord.setVolume(100L);
        dailyRecord.setDate(Date.from(LocalDate.now().plusDays(iDays).atStartOfDay(ZoneId.systemDefault()).toInstant()));;
        return dailyRecord;
    }
}
