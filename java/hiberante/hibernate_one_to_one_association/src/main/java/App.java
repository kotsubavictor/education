import org.hibernate.Session;

import java.util.Date;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate one to one (XML mapping)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Stock stock = new Stock();

        stock.setStockCode("4715");
        stock.setStockName("GENM");

        StockDetail stockDetail = new StockDetail();
        stockDetail.setCompName("GENTING Malaysia");
        stockDetail.setCompDesc("Best resort in the world");
        stockDetail.setRemark("Nothing Special");
        stockDetail.setListedDate(new Date());

        stock.setStockDetail(stockDetail);
        stockDetail.setStock(stock);

        session.save(stock);
        session.getTransaction().commit();

        System.out.println("Done");
        HibernateUtil.shutdown();
        System.out.println("exit");
    }
}
