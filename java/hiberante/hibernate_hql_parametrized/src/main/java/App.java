import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate one to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

//        named parameters
        Query<Stock> query = session.createQuery("from Stock where stockCode like :code ", Stock.class);
        query.setParameter("code", "%705%");
        List<Stock> list = query.list();
        System.out.println(list);

//        bean parameter
        Stock stock = new Stock();
        stock.setStockCode("7056");
        query = session.createQuery("from Stock where stockCode = :stockCode ", Stock.class);
        query.setProperties(stock);
        list = query.list();
        System.out.println(list);

//        positional parameter
        query = session.createQuery("from Stock where stockCode = ? ", Stock.class);
        query.setParameter(0, "7055");
        list = query.list();
        System.out.println(list);

        session.getTransaction().commit();
        System.out.println("Done");
        HibernateUtil.shutdown();
        System.out.println("exit");
    }
}
