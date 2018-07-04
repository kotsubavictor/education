package server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TEMPERATURE")
public class TemperatureSnapshot {

    @EmbeddedId
    private SnapshotPK snapshotPK;

    @Column(name = "MIN")
    private Float min;

    @Column(name = "MAX")
    private Float max;

    @Column(name = "AVG")
    private Float average;

    public TemperatureSnapshot() {
    }

    public TemperatureSnapshot(String name, Date date) {
        this.snapshotPK = new SnapshotPK(name, date);
    }

    public SnapshotPK getSnapshotPK() {
        return snapshotPK;
    }

    public void setSnapshotPK(SnapshotPK snapshotPK) {
        this.snapshotPK = snapshotPK;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}
