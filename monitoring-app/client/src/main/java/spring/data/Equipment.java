package spring.data;


public class Equipment {
    private String name;
    private long temperature;

    public Equipment() {
    }

    public Equipment(String name, long temp) {
        this.name = name;
        this.temperature = temp;
    }

    public String getName() {
        return name;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public void setName(String name) {
        this.name = name;
    }
}