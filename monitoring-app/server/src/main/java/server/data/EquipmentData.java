package server.data;

import java.io.Serializable;

public class EquipmentData implements Serializable {
    private String name;
    private Boolean online;
    private Float temperature;
    private Float humidity;

    public EquipmentData() {
    }

    public EquipmentData(String name, Boolean online, Float temperature, Float humidity) {
        this.name = name;
        this.online = online;
        this.temperature = temperature;
        this.humidity = humidity;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "EquipmentData{" +
                "name='" + name + '\'' +
                ", online=" + online +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }

    public static EquipmentData from(server.domain.Equipment equipment) {
        return new EquipmentData(equipment.getName(), equipment.getOnline(),
                equipment.getTemperature(), equipment.getHumidity());
    }
}