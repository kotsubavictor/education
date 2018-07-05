package server.data;

import server.domain.AccountSnapshot;

import javax.persistence.*;
import java.util.Date;

public class AccountSnapshotData {

    private long date;

    private Double npAmount;

    private Double sxAmount;

    public AccountSnapshotData() {
    }

    public AccountSnapshotData(long date, double npAmount, double sxAmount) {
        this.date = date;
        this.npAmount = npAmount;
        this.sxAmount = sxAmount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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

    public static AccountSnapshotData from(AccountSnapshot accountSnapshot) {
        return new AccountSnapshotData(accountSnapshot.getDate().getTime(), accountSnapshot.getNpAmount(), accountSnapshot.getSxAmount());
    }

    @Override
    public String toString() {
        return "AccountSnapshot{" +
                ", date=" + date +
                ", npAmount=" + npAmount +
                ", sxAmount=" + sxAmount +
                '}';
    }
}
