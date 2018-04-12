import org.hibernate.Session;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate one to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

//      todo: Immutable object
//      If mutable = “false” or @Immutable is declared in class element,
//      it means the updates to this class will be ignored,
//      but no exception is thrown, only the add and delete operation are allow.

//      Immutable object - Stock
//      Insert is allowed for stock;
//      todo: stock: insert into hibernate.stock (T1_STOCK_CODE, T1_STOCK_NAME) values (?, ?)
//      todo: daily_record: insert into hibernate.stock_daily_record (T2_DATE, T2_PRICE_CHANGE, T2_PRICE_CLOSE, T2_PRICE_OPEN, T2_STOCK_ID, T2_VOLUME) values (?, ?, ?, ?, ?, ?)
            Stock stock = create(2);
            session.save(stock);
            session.getTransaction().commit();
            session.beginTransaction();

//      Update is forbidden.
//      Update will not be executed, but no exception is thrown.
//      todo: stock: nothing
            stock.setStockName("new_name");
            session.update(stock);
            session.getTransaction().commit();
            session.beginTransaction();

//      Delete is allowed for stock
//      todo: stock: delete from hibernate.stock where T1_STOCK_ID=?
//      todo: daily_record: delete from hibernate.stock_daily_record where T2_RECORD_ID=?
            session.delete(stock);
            session.getTransaction().commit();
            session.beginTransaction();


//      todo: Immutable dependency - collection - daily records
//      If mutable = “false” or @Immutable is declared in collection,
//      it means the add and delete-orphan are not allow in this collection,
//      with exception throw, only update and ‘cascade delete all’ are allow.

//            works
            Stock stock1 = create(2, true);;
            session.save(stock1);
            session.getTransaction().commit();

            session.beginTransaction();
            session.delete(stock1);
            session.getTransaction().commit();

//            doest not work
            stock1 = create(2, true);;
            session.beginTransaction();
            session.save(stock1);
            session.getTransaction().commit();

            session.beginTransaction();
            StockDailyRecord record = createRecord(3);
            record.setStock(stock1);
//            both variant will not work
            stock1.getStockDailyRecords().add(record);
//            stock1.getStockDailyRecords().clear();

//      does not work + throws exception, because update is not allowed
            try {
                session.update(stock1);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Rollback transaction due to:");
                System.out.println(e);
//      Entity is still broken. It contains inserted data.
                System.out.println(stock1);
                stock1.getStockDailyRecords().remove(record);
                session.getTransaction().rollback();
                session.close();
            }
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
