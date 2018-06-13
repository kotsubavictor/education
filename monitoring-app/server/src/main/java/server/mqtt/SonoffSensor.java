package server.mqtt;

import com.google.gson.annotations.SerializedName;

public class SonoffSensor {

    @SerializedName("Time")
    private String time;

    @SerializedName("AM2301")
    private AM2301Sensor am2301;

    @SerializedName("TempUnit")
    private String tempUnit;

    public class AM2301Sensor {

        @SerializedName("Temperature")
        private Float temperature;

        @SerializedName("Humidity")
        private Float humidity;

        public Float getTemperature() {
            return temperature;
        }

        public void setTemperature(Float temperature) {
            this.temperature = temperature;
        }

        public Float getHumidity() {
            return humidity;
        }

        public void setHumidity(Float humidity) {
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return "AM2301Sensor{" +
                    "temperature=" + temperature +
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
        return am2301.getTemperature();
    }

    public Float getHumidity() {
        return am2301.getHumidity();
    }

    @Override
    public String toString() {
        return "SonoffSensor{" +
                "time='" + time + '\'' +
                ", am2301=" + am2301 +
                ", tempUnit='" + tempUnit + '\'' +
                '}';
    }
}

