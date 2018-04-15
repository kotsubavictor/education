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
            fetchSelect(session, id);
            System.out.println("\n-----------------------------\n");
            fetchJoin(session, id);
            System.out.println("\n-----------------------------\n");
            fetchSelectBatchedBy10(session);
            System.out.println("\n-----------------------------\n");
            fetchSubSelect(session);
            session.getTransaction().commit();
            System.out.println("Done");
        } finally {
            HibernateUtil.shutdown();
            System.out.println("exit");
        }
    }

    private static void fetchSelect(Session session, Integer id) {
        System.out.println("fetchSelect");
        // Fetch.SELECT - lazy loading
        //call select from stock
        Stock stock = session.get(Stock.class, id);
        Set sets = stock.getStockDailyRecords();

        //call select from stock_daily_record
        for (Iterator iter = sets.iterator(); iter.hasNext(); ) {
            StockDailyRecord sdr = (StockDailyRecord) iter.next();
            System.out.println(sdr.getRecordId());
            System.out.println(sdr.getDate());
        }
    }

    private static void fetchSelectBatchedBy10(Session session) {
        System.out.println("fetchSelectBatchedBy10");
        // Fetch.SELECT + BatchSize = 10 - lazy loading
        //call select from stock
        List<Stock> stocks = session.createQuery("from Stock", Stock.class).list();
        stocks.forEach(stock -> {
        //call select from daily records for the first ten records of stocks and so on....
        // select * from stock_daily_record where T2_STOCK_ID in (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) - 10xSTOCK_ID
            System.out.println(stock.getStockName());
            stock.getStockDailyRecords().forEach(sdr -> {
                System.out.println(sdr.getRecordId());
                System.out.println(sdr.getDate());
            });
        });
    }

    private static void fetchJoin(Session session, Integer id) {
        System.out.println("fetchJoin");
        // Fetch.JOIN - lazy loading
        // call join-select: stocks and daily records are loaded by one query
        Stock1 stock = session.get(Stock1.class, id);
        Set sets = stock.getStockDailyRecords();
        for (Iterator iter = sets.iterator(); iter.hasNext(); ) {
            StockDailyRecord sdr = (StockDailyRecord) iter.next();
            System.out.println(sdr.getRecordId());
            System.out.println(sdr.getDate());
        }
    }

    private static void fetchSubSelect(Session session) {
        System.out.println("fetchSubSelect");
        // loads records from stock table
        // select * from stock where T1_STOCK_ID in (34 , 35)
        // select * from stock_daily_record where T2_STOCK_ID in
        //      (select T1_STOCK_ID from stock where T1_STOCK_ID in (34 , 35))
        List<Stock2> list = session.createQuery("from Stock2 where stockId in (34, 35)").list();

        for(Stock2 stock : list) {
            System.out.println(stock.getStockName());
        //loads daily records for two stocks with a subselect
            Set sets = stock.getStockDailyRecords();
            for ( Iterator iter = sets.iterator();iter.hasNext(); ) {
                StockDailyRecord sdr = (StockDailyRecord) iter.next();
                System.out.println(sdr.getRecordId());
                System.out.println(sdr.getDate());
            }
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
