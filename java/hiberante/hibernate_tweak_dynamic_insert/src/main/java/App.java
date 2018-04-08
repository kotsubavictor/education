import org.hibernate.Filter;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
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

//          In case of dynamic-insert = true, null property will not be used in insert statement.
//          todo: insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (?, ?)
            session.save(new Stock("code_f", "name_f", null));

//          In case of dynamic-insert = false, null property will be used in insert statement.
//          todo: insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME, T1_STOCK_OTHER) values (?, ?, ?)
            session.save(new Stock1("code_f1", "name_f1", null));

            session.getTransaction().commit();

            System.out.println("Done");
        } finally {
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    private static final Stock create(int amount) {
        final Stock stock = new Stock("code_f", "name_f", null);
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
