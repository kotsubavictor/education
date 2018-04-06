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

//            criteria
            session.beginTransaction();

//            return an entity
            NativeQuery<Stock> query = session.createNativeQuery(
                    "select * from stock s where s.T1_STOCK_CODE = :stockCode")
                    .addEntity(Stock.class)
                    .setParameter("stockCode", "7052");
            List<Stock> result = query.list();
            System.out.println(result);

//          returns a field
            NativeQuery query1 = session.createNativeQuery(
                    "select s.T1_STOCK_CODE from stock s where s.T1_STOCK_CODE = :stockCode")
                    .setParameter("stockCode", "7052");
            List result1 = query1.list();
            System.out.println(result1);


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
