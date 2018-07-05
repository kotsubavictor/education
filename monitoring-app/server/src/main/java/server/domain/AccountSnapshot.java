package server.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT_SNAPSHOT")
public class AccountSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "np")
    private Double npAmount;

    @Column(name = "sx")
    private Double sxAmount;

    public AccountSnapshot() {
    }

    public AccountSnapshot(Date date, double npAmount, double sxAmount) {
        this.date = date;
        this.npAmount = npAmount;
        this.sxAmount = sxAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getNpAmount() {
        return npAmount;
    }

    public void setNpAmount(double npAmount) {
        this.npAmount = npAmount;
    }

    public double getSxAmount() {
        return sxAmount;
    }

    public void setSxAmount(double sxAmount) {
        this.sxAmount = sxAmount;
    }

    @Override
    public String toString() {
        return "AccountSnapshot{" +
                "id=" + id +
                ", date=" + date +
                ", npAmount=" + npAmount +
                ", sxAmount=" + sxAmount +
                '}';
    }
}
