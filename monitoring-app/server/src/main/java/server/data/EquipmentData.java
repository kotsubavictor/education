package server.data;

import java.io.Serializable;

public class EquipmentData implements Serializable {
    private String name;
    private Float temperature;

    public EquipmentData() {
    }

    public EquipmentData(String name, Float temp) {
        this.name = name;
        this.temperature = temp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + name + '\'' +
                ", temperature=" + temperature +
                '}';
    }

    public static EquipmentData from(server.domain.Equipment equipment) {
        return new EquipmentData(equipment.getName(), equipment.getTemperature());
    }
}