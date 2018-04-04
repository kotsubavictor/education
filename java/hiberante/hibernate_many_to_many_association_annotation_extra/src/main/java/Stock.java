import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "stock", catalog = "hibernate", uniqueConstraints = {
        @UniqueConstraint(columnNames = "T1_STOCK_NAME"),
        @UniqueConstraint(columnNames = "T1_STOCK_CODE") })
public class Stock {

    private Integer stockId;
    private String stockCode;
    private String stockName;
    private Set<StockCategory> stockCategories = new HashSet<StockCategory>(0);

    public Stock() {
    }

    public Stock(String stockCode, String stockName) {
        this.stockCode = stockCode;
        this.stockName = stockName;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T1_STOCK_ID", unique = true, nullable = false)
    public Integer getStockId() {
        return this.stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Column(name = "T1_STOCK_CODE", unique = true, nullable = false, length = 10)
    public String getStockCode() {
        return this.stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Column(name = "T1_STOCK_NAME", unique = true, nullable = false, length = 20)
    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.stock", cascade=CascadeType.ALL)
    public Set<StockCategory> getStockCategories() {
        return stockCategories;
    }

    public void setStockCategories(Set<StockCategory> stockCategories) {
        this.stockCategories = stockCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockCode, stock.stockCode) &&
                Objects.equals(stockName, stock.stockName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stockCode, stockName);
    }
}
