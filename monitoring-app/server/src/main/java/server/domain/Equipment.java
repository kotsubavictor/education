package server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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