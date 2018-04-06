package other;

import interceptor.IAuditLog;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@FilterDef(name="stockRecordFilter",
        parameters=@ParamDef( name="stockRecordFilterParam", type="date" ) )
@Entity
@Table(name = "stock", catalog = "hibernate", uniqueConstraints = {
        @UniqueConstraint(columnNames = "T1_STOCK_NAME"),
        @UniqueConstraint(columnNames = "T1_STOCK_CODE") })
public class Stock implements java.io.Serializable, IAuditLog {

    private static final long serialVersionUID = 1L;


    private Integer stockId;
    private String stockCode;
    private String stockName;
    private Set<StockDailyRecord> stockDailyRecords = new HashSet<>(
            0);

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Filter(
            name = "stockRecordFilter",
            condition="T2_DATE >= :stockRecordFilterParam"
    )
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

    @Transient
    @Override
    public Long getId() {
        return stockId.longValue();
    }

    @Transient
    @Override
    public String getLogDeatil() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Stock Id : ").append(stockId)
                .append(" Stock Code : ").append(stockCode)
                .append(" Stock Name : ").append(stockName);
        return sb.toString();
    }
}
