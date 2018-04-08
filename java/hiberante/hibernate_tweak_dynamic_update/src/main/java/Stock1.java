import java.util.HashSet;
import java.util.Set;

public class Stock1 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private Integer stockId;
    private String stockCode;
    private String stockName;
    private String stockOther;
    private Set<StockDailyRecord> stockDailyRecords =
            new HashSet<>(0);

    public Stock1() {
    }

    public Stock1(String stockCode, String stockName, String stockOther) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.stockOther = stockOther;
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

    public String getStockOther() {
        return stockOther;
    }

    public void setStockOther(String stockOther) {
        this.stockOther = stockOther;
    }

    @Override
    public String toString() {
        return "Stock1{" +
                "stockId=" + stockId +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", stockOther='" + stockOther + '\'' +
                ", stockDailyRecords=" + stockDailyRecords +
                '}';
    }
}
