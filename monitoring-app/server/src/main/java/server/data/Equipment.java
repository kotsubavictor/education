package server.data;

public class Equipment {
    private String name;
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
}