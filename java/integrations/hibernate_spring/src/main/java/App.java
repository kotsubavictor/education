import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.config.AppConfig;
import stock.bo.StockBo;
import stock.model.Stock;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        StockBo stockBo = context.getBean(StockBo.class);

        // Get Stock
        Stock stock = stockBo.findByStockCode("7052");
        System.out.println(stock);

        context.close();
    }
}
