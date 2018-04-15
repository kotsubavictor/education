import org.springframework.context.support.ClassPathXmlApplicationContext;
import stock.bo.StockBo;
import stock.model.Stock;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

        StockBo stockBo = context.getBean(StockBo.class);

        // Get Stock
        Stock stock = stockBo.findByStockCode("7052");
        System.out.println(stock);

        context.close();
    }
}
