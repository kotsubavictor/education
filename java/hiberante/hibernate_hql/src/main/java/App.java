import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate one to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Query<Stock> query = session.createQuery("from Stock where stockCode like :code ", Stock.class);
        query.setParameter("code", "%705%");
        List<Stock> list = query.list();
        System.out.println(list);

        query = session.createQuery("from Stock where stockCode = '7052' ", Stock.class);
        list = query.list();
        System.out.println(list);

        query = session.createQuery("delete Stock where stockCode = :stockCode");
        query.setParameter("stockCode", "7052");
        int result = query.executeUpdate();
        System.out.println("deleted 7052 = " + result);

        query = session.createQuery("delete Stock where stockCode = '7053'");
        result = query.executeUpdate();
        System.out.println("deleted 7053 = " + result);

//      In HQL, only the INSERT INTO … SELECT … is supported;
//      there is no INSERT INTO … VALUES.
//      HQL only support insert from another table
        query = session.createQuery("insert into Stock(stockCode, stockName)" +
                "select c.stockCode, c.stockName from StockBackUp c");
        result = query.executeUpdate();
        System.out.println("inserted = " + result);

        session.getTransaction().commit();
        System.out.println("Done");
        HibernateUtil.shutdown();
        System.out.println("exit");
    }
}
