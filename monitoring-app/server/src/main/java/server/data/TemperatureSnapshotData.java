package server.data;

import java.util.Collection;

public class TemperatureSnapshotData {

    private Long date;
    private Collection<Temperature> temperatures;

    public TemperatureSnapshotData() {
    }

    public TemperatureSnapshotData(Long date, Collection<Temperature> temperatures) {
        this.date = date;
        this.temperatures = temperatures;
    }

    public Long getDate() {
        return date;
    }

    public Collection<Temperature> getTemperatures() {
        return temperatures;
    }

    @Override
    public String toString() {
        return "TemperatureSnapshot{" +
                "date=" + date +
                ", temperatures=" + temperatures +
                '}';
    }

    public static class Temperature {

        private String name;

        private Float min;

        private Float max;

        private Float average;

        public Temperature() {
        }

        public Temperature(String name, Float min, Float max, Float average) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.average = average;
        }

        public String getName() {
            return name;
        }

        public Float getMin() {
            return min;
        }

        public Float getMax() {
            return max;
        }

        public Float getAverage() {
            return average;
        }

        @Override
        public String toString() {
            return "Temperature{" +
                    "name='" + name + '\'' +
                    ", min=" + min +
                    ", max=" + max +
                    ", average=" + average +
                    '}';
        }

        public static Temperature from(server.domain.TemperatureSnapshot snapshot) {
            return new Temperature(snapshot.getSnapshotPK().getName(), snapshot.getMin(), snapshot.getMax(), snapshot.getAverage());
        }
    }
}
