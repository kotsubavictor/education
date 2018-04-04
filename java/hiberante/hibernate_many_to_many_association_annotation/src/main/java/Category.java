import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", catalog = "hibernate")
public class Category {

    private Integer categoryId;
    private String name;
    private String desc;
    private Set<Stock> stocks = new HashSet<>(0);

    public Category() {
    }

    public Category(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Category(String name, String desc, Set<Stock> stocks) {
        this.name = name;
        this.desc = desc;
        this.stocks = stocks;
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    public Set<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

}
