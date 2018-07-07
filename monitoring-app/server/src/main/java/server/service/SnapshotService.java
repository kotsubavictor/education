package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.data.TemperatureSnapshotData;
import server.domain.TemperatureSnapshot;
import server.repository.TemperatureSnapshotRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Scope(value = "singleton")
public class SnapshotService {

    @Autowired
    private TemperatureSnapshotRepository temperatureSnapshotRepository;

    @Autowired
    private PushService pushService;

    private Map<String, Collection<Float>> snapshots = new ConcurrentHashMap<>();

    public void record(EquipmentData equipment) {
        Collection<Float> snapshot = snapshots.get(equipment.getName());
        if (snapshot == null) {
            snapshot = new LinkedList<>();
            snapshots.put(equipment.getName(), snapshot);
        }
        snapshot.add(equipment.getTemperature());
    }

    @Scheduled(initialDelay = 300000, fixedRate = 300000)
    public List<TemperatureSnapshotData.Temperature> flush() {
        final Date date = new Date();
        final Collection<TemperatureSnapshot> data = new LinkedList<>();
        snapshots.entrySet().forEach(entry -> {
            Collection<Float> temperatures = entry.getValue();

            TemperatureSnapshot snapshot = new TemperatureSnapshot(entry.getKey(), date);
            snapshot.setMin(1000f);
            snapshot.setMax(-1000f);
            snapshot.setAverage(0f);

            float sum = 0;
            for (Float temperature : temperatures) {
                snapshot.setMin(Math.min(snapshot.getMin(), temperature));
                snapshot.setMax(Math.max(snapshot.getMax(), temperature));
                sum += temperature;
            }

            snapshot.setAverage(sum / temperatures.size());
            snapshot = temperatureSnapshotRepository.save(snapshot);
            data.add(snapshot);

        });

        List<TemperatureSnapshotData.Temperature> temperatures;
        if (!data.isEmpty()) {
            temperatures = data.stream().map(TemperatureSnapshotData.Temperature::from).collect(Collectors.toList());
            pushService.send(new TemperatureSnapshotData(date.getTime(), temperatures));
        } else {
            temperatures = Collections.emptyList();
        }
        snapshots.clear();
        return temperatures;
    }
}
