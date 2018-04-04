import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stock", catalog = "hibernate", uniqueConstraints = {
        @UniqueConstraint(columnNames = "T1_STOCK_NAME"),
        @UniqueConstraint(columnNames = "T1_STOCK_CODE") })
public class Stock {

    private Integer stockId;
    private String stockCode;
    private String stockName;
    private Set<Category> categories = new HashSet<>(0);

    public Stock() {
    }

    public Stock(String stockCode, String stockName) {
        this.stockCode = stockCode;
        this.stockName = stockName;
    }

    public Stock(String stockCode, String stockName, Set<Category> categories) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.categories = categories;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "stock_category", catalog = "hibernate", joinColumns = {
            @JoinColumn(name = "T3_STOCK_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "T3_CATEGORY_ID",
                    nullable = false, updatable = false) })
    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
