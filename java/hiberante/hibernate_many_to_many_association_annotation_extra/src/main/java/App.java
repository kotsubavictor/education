import org.hibernate.Session;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        System.out.println("Hibernate many to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Stock stock = new Stock();
        stock.setStockCode("7052");
        stock.setStockName("PADINI");

        Category category1 = new Category("CONSUMER", "CONSUMER COMPANY");
        //new category, need save to get the id first
        session.save(category1);

        StockCategory stockCategory = new StockCategory();
        stockCategory.setStock(stock);
        stockCategory.setCategory(category1);
        stockCategory.setCreatedDate(new Date()); //extra column
        stockCategory.setCreatedBy("system"); //extra column

        stock.getStockCategories().add(stockCategory);

        session.save(stock);

        session.getTransaction().commit();
        HibernateUtil.shutdown();
        System.out.println("exit");
    }
}
