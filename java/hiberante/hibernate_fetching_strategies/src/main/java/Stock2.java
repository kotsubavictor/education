import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stock", catalog = "hibernate", uniqueConstraints = {
        @UniqueConstraint(columnNames = "T1_STOCK_NAME"),
        @UniqueConstraint(columnNames = "T1_STOCK_CODE") })
public class Stock2 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private Integer stockId;
    private String stockCode;
    private String stockName;
    private Set<StockDailyRecord> stockDailyRecords = new HashSet<>(
            0);

    public Stock2() {
    }

    public Stock2(String stockCode, String stockName) {
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

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    public Set<StockDailyRecord> getStockDailyRecords() {
        return stockDailyRecords;
    }

    public void setStockDailyRecords(Set<StockDailyRecord> stockDailyRecords) {
        this.stockDailyRecords = stockDailyRecords;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", stockDailyRecords=" + stockDailyRecords +
                '}';
    }
}
