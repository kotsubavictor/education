package server.mqtt;

import com.google.gson.annotations.SerializedName;

public class SonoffState {

    @SerializedName("Time")
    private String time;

    @SerializedName("Uptime")
    private String uptime;

    @SerializedName("Vcc")
    private Float vcc;

    @SerializedName("POWER")
    private String power;

    @SerializedName("Wifi")
    private SonoffWifi wifi;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public Float getVcc() {
        return vcc;
    }

    public void setVcc(Float vcc) {
        this.vcc = vcc;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public SonoffWifi getWifi() {
        return wifi;
    }

    public void setWifi(SonoffWifi wifi) {
        this.wifi = wifi;
    }

    @Override
    public String toString() {
        return "SonoffState{" +
                "time='" + time + '\'' +
                ", uptime='" + uptime + '\'' +
                ", vcc=" + vcc +
                ", power='" + power + '\'' +
                ", wifi='" + wifi + '\'' +
                '}';
    }

    public class SonoffWifi {

        @SerializedName("AP")
        private String ap;

        @SerializedName("SSId")
        private String ssid;

        @SerializedName("RSSI")
        private String rssi;

        @SerializedName("APMac")
        private String apmac;

        public String getAp() {
            return ap;
        }

        public void setAp(String ap) {
            this.ap = ap;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getRssi() {
            return rssi;
        }

        public void setRssi(String rssi) {
            this.rssi = rssi;
        }

        public String getApmac() {
            return apmac;
        }

        public void setApmac(String apmac) {
            this.apmac = apmac;
        }

        @Override
        public String toString() {
            return "SonoffWifi{" +
                    "ap='" + ap + '\'' +
                    ", ssid='" + ssid + '\'' +
                    ", rssi='" + rssi + '\'' +
                    ", apmac='" + apmac + '\'' +
                    '}';
        }
    }
}
