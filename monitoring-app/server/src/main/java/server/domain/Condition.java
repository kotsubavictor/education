package server.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "COND")
public class Condition {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "COND", length = 3000)
    private String condition;

    @Column(name = "MANUAL")
    private Boolean manual;

    @Column(name = "ACTIVE")
    private Boolean active;

    public Condition() {
    }

    public Condition(String name, String condition, Boolean manual, Boolean active) {
        this.name = name;
        this.condition = condition;
        this.manual = manual;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return Objects.equals(name, condition.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getManual() {
        return manual;
    }

    public void setManual(Boolean manual) {
        this.manual = manual;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                ", manual=" + manual +
                ", active=" + active +
                '}';
    }
}
