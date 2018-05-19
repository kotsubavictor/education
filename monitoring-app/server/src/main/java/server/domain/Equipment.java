package server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "EQUIPMENT")
public class Equipment {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "TEMPERATURE")
    private Float temperature;

    public Equipment() {
    }

    public Equipment(String name, Float temp) {
        this.name = name;
        this.temperature = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equals(name, equipment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void setName(String name) {
        this.name = name;
    }
}