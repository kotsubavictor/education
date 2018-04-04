import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category", catalog = "hibernate")
public class Category {

    private Integer categoryId;
    private String name;
    private String desc;
    private Set<StockCategory> stockCategories = new HashSet<StockCategory>(0);


    public Category() {
    }

    public Category(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T2_CATEGORY_ID", unique = true, nullable = false)
    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "T2_NAME", nullable = false, length = 10)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "T2_DESC", nullable = false)
    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.category")
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
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(desc, category.desc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, desc);
    }
}
