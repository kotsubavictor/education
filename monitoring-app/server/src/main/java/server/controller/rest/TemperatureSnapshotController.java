package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.data.TemperatureSnapshotData;
import server.domain.TemperatureSnapshot;
import server.repository.TemperatureSnapshotRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/temperatures")
public class TemperatureSnapshotController {

    @Autowired
    private TemperatureSnapshotRepository temperatureSnapshotRepository;

    @RequestMapping
    public Collection<TemperatureSnapshotData> list() {
        Map<java.util.Date, Collection<TemperatureSnapshot>> data = new TreeMap<>();
        List<TemperatureSnapshotData> result = new LinkedList<>();

        List<server.domain.TemperatureSnapshot> snapshots = temperatureSnapshotRepository
                .getAllBySnapshotPK_DateAfterAndSnapshotPK_DateBefore(
                        Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                        Timestamp.valueOf(LocalDateTime.now())
                );

        snapshots.forEach(snapshot -> {
            Date date = snapshot.getSnapshotPK().getDate();
            Collection<TemperatureSnapshot> temperatures = data.get(date);
            if (temperatures == null) {
                temperatures = new LinkedList<>();
                data.put(date, temperatures);
            }
            temperatures.add(snapshot);
        });

        data.forEach((date, temperatureSnapshots) -> {
            result.add(new TemperatureSnapshotData(
                    date.getTime(),
                    temperatureSnapshots.stream().map(TemperatureSnapshotData.Temperature::from)
                            .collect(Collectors.toList()))
            );
        });

        return result;
    }
}