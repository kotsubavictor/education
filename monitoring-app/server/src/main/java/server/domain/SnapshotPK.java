package server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class SnapshotPK implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "SNAPSHOT")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    public SnapshotPK() {
    }

    public SnapshotPK(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnapshotPK that = (SnapshotPK) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, date);
    }
}
