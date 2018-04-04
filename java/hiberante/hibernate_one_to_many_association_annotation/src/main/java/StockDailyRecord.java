import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "stock_daily_record", catalog = "hibernate",
        uniqueConstraints = @UniqueConstraint(columnNames = "T2_DATE"))
public class StockDailyRecord {
    private Integer recordId;
    private Stock stock;
    private Float priceOpen;
    private Float priceClose;
    private Float priceChange;
    private Long volume;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T2_RECORD_ID", unique = true, nullable = false)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T2_STOCK_ID", nullable = false)
    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Column(name = "T2_PRICE_OPEN", precision = 6)
    public Float getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(Float priceOpen) {
        this.priceOpen = priceOpen;
    }

    @Column(name = "T2_PRICE_CLOSE", precision = 6)
    public Float getPriceClose() {
        return priceClose;
    }

    public void setPriceClose(Float priceClose) {
        this.priceClose = priceClose;
    }

    @Column(name = "T2_PRICE_CHANGE", precision = 6)
    public Float getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(Float priceChange) {
        this.priceChange = priceChange;
    }

    @Column(name = "T2_VOLUME")
    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "T2_DATE", unique = true, nullable = false, length = 10)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
