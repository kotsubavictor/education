package server.data;

import server.domain.Condition;

public class ConditionData {
    private String name;
    private String condition;
    private Boolean manual;
    private Boolean active;

    public ConditionData() {
    }

    public ConditionData(String name, String condition, Boolean manual, Boolean active) {
        this.name = name;
        this.condition = condition;
        this.manual = manual;
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
        return "ReleConditionData{" +
                "name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                ", manual=" + manual +
                ", active=" + active +
                '}';
    }

    public static ConditionData from(Condition data) {
        return new ConditionData(data.getName(), data.getCondition(),
                data.getManual(), data.getActive());
    }
}
