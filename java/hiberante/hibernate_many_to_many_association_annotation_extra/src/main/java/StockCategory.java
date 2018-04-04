import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_category", catalog = "hibernate")
@AssociationOverrides({
        @AssociationOverride(name = "pk.stock",
                joinColumns = @JoinColumn(name = "T3_STOCK_ID")),
        @AssociationOverride(name = "pk.category",
                joinColumns = @JoinColumn(name = "T3_CATEGORY_ID")) })
public class StockCategory {

    private StockCategoryId pk = new StockCategoryId();
    private Date createdDate;
    private String createdBy;

    public StockCategory() {
    }

    @EmbeddedId
    public StockCategoryId getPk() {
        return pk;
    }

    public void setPk(StockCategoryId pk) {
        this.pk = pk;
    }

    @Transient
    public Stock getStock() {
        return getPk().getStock();
    }

    public void setStock(Stock stock) {
        getPk().setStock(stock);
    }

    @Transient
    public Category getCategory() {
        return getPk().getCategory();
    }

    public void setCategory(Category category) {
        getPk().setCategory(category);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "T3_CREATED_DATE", nullable = false, length = 10)
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "T3_CREATED_BY", nullable = false, length = 10)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StockCategory that = (StockCategory) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
