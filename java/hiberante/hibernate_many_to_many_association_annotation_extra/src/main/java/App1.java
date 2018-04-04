import org.hibernate.Session;

import java.util.Date;

public class App1 {
    public static void main(String[] args) {
        System.out.println("Hibernate many to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Stock stock = new Stock();
        stock.setStockCode("7052");
        stock.setStockName("PADINI");

        //assume category id is 1
        Category category1 = (Category)session.get(Category.class, 1);

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
