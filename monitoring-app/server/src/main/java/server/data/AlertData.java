package server.data;

import server.domain.Alert;

public class AlertData {
    private String name;
    private String condition;
    private Boolean active;

    public AlertData() {
    }

    public AlertData(String name, String condition, Boolean active) {
        this.name = name;
        this.condition = condition;
        this.active = active;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ReleConditionData{" +
                "name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                ", active=" + active +
                '}';
    }

    public static AlertData from(Alert data) {
        return new AlertData(data.getName(), data.getCondition(), data.getActive());
    }
}
