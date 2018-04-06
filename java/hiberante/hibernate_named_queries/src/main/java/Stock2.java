import java.util.HashSet;
import java.util.Set;

public class Stock2 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private Integer stockId;
    private String stockCode;
    private String stockName;
    private Set<StockDailyRecord> stockDailyRecords =
            new HashSet<>(0);

    public Stock2() {
    }

    public Stock2(String stockCode, String stockName) {
        this.stockCode = stockCode;
        this.stockName = stockName;
    }

    public Integer getStockId() {
        return this.stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockCode() {
        return this.stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Set<StockDailyRecord> getStockDailyRecords() {
        return stockDailyRecords;
    }

    public void setStockDailyRecords(Set<StockDailyRecord> stockDailyRecords) {
        this.stockDailyRecords = stockDailyRecords;
    }

    @Override
    public String toString() {
        return "Stock2{" +
                "stockId=" + stockId +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", stockDailyRecords=" + stockDailyRecords +
                '}';
    }
}
