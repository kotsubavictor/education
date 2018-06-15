package server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RELE")
public class Rele {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "POWER")
    private Boolean power;

    public Rele() {
    }

    public Rele(String name, Boolean power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPower() {
        return power;
    }

    public void setPower(Boolean power) {
        this.power = power;
    }
}
