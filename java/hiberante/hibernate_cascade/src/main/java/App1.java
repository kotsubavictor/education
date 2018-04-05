import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;

public class App1 {
    public static void main(String[] args) {
        System.out.println("Hibernate one to many (XML Mapping)");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

//        todo: there is no cascade = delete, hence we have to remove daily records manually
//        Query q = session.createQuery("from Stock where stockCode = :stockCode ");
//        q.setParameter("stockCode", "7052");
//        Stock stock = (Stock)q.list().get(0);
//        todo: removing daily records
//        for (StockDailyRecord sdr : stock.getStockDailyRecords()){
//            session.delete(sdr);
//        }


//        todo: in case of cascade = delete, daily records will be removed automatically
//        todo: cascade options can be applied simalteniously cascade = 'save-update, delete'
        Query<Stock1> q = session.createQuery("from Stock1 where stockCode = :stockCode ", Stock1.class);
        q.setParameter("stockCode", "7052");
        Stock1 stock = q.list().get(0);

        session.delete(stock);

        session.getTransaction().commit();
        System.out.println("Done");
        HibernateUtil.shutdown();
        System.out.println("exit");
    }
}
