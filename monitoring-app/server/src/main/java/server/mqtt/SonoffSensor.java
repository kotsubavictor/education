package server.mqtt;

import com.google.gson.annotations.SerializedName;

public class SonoffSensor {

    @SerializedName("Time")
    private String time;

    @SerializedName("AM2301")
    private AM2301Sensor am2301;

    @SerializedName("DS18B20")
    private DS18B20Sensor ds18b20;

    @SerializedName("TempUnit")
    private String tempUnit;

    public class Sensor {
        @SerializedName("Temperature")
        private Float temperature;

        public Float getTemperature() {
            return temperature;
        }

        public void setTemperature(Float temperature) {
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return "Sensor{" +
                    "temperature=" + temperature +
                    '}';
        }
    }

    public class DS18B20Sensor extends Sensor {
    }

    public class AM2301Sensor extends Sensor {

        @SerializedName("Humidity")
        private Float humidity;

        public Float getHumidity() {
            return humidity;
        }

        public void setHumidity(Float humidity) {
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return "AM2301Sensor{" +
                    "temperature=" + getTemperature() +
                    ", humidity=" + humidity +
                    '}';
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AM2301Sensor getAm2301() {
        return am2301;
    }

    public void setAm2301(AM2301Sensor am2301) {
        this.am2301 = am2301;
    }

    public String getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    public Float getTemperature() {
        Float temperature = 0F;

        if (am2301 != null) {
            temperature = am2301.getTemperature();
        } else if (ds18b20 != null) {
            temperature = ds18b20.getTemperature();
        }

        return temperature;
    }

    public Float getHumidity() {
        Float humidity = 0F;

        if (am2301 != null) {
            humidity = am2301.getHumidity();
        }

        return humidity;
    }

    @Override
    public String toString() {
        return "SonoffSensor{" +
                "time='" + time + '\'' +
                ", am2301=" + am2301 +
                ", ds18b20=" + ds18b20 +
                ", tempUnit='" + tempUnit + '\'' +
                '}';
    }
}

