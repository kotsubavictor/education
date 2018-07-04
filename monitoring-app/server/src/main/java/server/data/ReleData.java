package server.data;

import server.domain.Rele;

public class ReleData {

    private String name;

    private Boolean power;

    public ReleData() {
    }

    public ReleData(String name, Boolean power) {
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

    public static ReleData from(Rele equipment) {
        return new ReleData(equipment.getName(), equipment.getPower());
    }
}
