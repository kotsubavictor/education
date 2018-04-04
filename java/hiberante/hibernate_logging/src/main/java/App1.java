import org.hibernate.Session;

import java.util.Date;

public class App1 {
    public static void main(String[] args) {
        System.out.println("Hibernate many to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Stock stock = (Stock)session.get(Stock.class, 34);

        StockDailyRecord stockDailyRecords = new StockDailyRecord();
        stockDailyRecords.setPriceOpen(new Float("1.2"));
        stockDailyRecords.setPriceClose(new Float("1.1"));
        stockDailyRecords.setPriceChange(new Float("10.0"));
        stockDailyRecords.setVolume(3000000L);
        stockDailyRecords.setDate(new Date());

        stockDailyRecords.setStock(stock);
        stock.getStockDailyRecords().add(stockDailyRecords);

        session.save(stockDailyRecords);
        session.update(stock);

        session.getTransaction().commit();
        HibernateUtil.shutdown();
        System.out.println("exit");
    }
}
