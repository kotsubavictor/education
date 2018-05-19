package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.data.TemperatureSnapshotData;
import server.domain.TemperatureSnapshot;
import server.repository.EquipmentRepository;
import server.repository.TemperatureSnapshotRepository;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Scope(value = "singleton")
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private TemperatureSnapshotRepository temperatureSnapshotRepository;

    @Autowired
    private PushService pushService;

    private Map<String, Collection<Float>> snapshots = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 300000)
    public void saveSnapshots() {
        final Date date = new Date();
        final Collection<TemperatureSnapshot> data = new LinkedList<>();
        snapshots.entrySet().forEach(entry -> {
            TemperatureSnapshot snapshot = new TemperatureSnapshot(entry.getKey(), date);
            snapshot.setMin(100f);
            snapshot.setMax(-100f);
            Collection<Float> temperatures = entry.getValue();
            float sum = 0;

            for (Float temperature : temperatures) {
                snapshot.setMin(Math.min(snapshot.getMin(), temperature));
                snapshot.setMax(Math.max(snapshot.getMax(), temperature));
                sum += temperature;
            }

            snapshot.setAverage(sum / temperatures.size());
            temperatureSnapshotRepository.save(snapshot);
            data.add(snapshot);
        });
        snapshots.clear();

        if (!data.isEmpty()) {
            pushService.sendTemperatureSnapshot(
                    new TemperatureSnapshotData(
                            date.getTime(),
                            data.stream().map(TemperatureSnapshotData.Temperature::from)
                                    .collect(Collectors.toList())
                    )
            );
        }
    }

    public void save(EquipmentData equipment) {
        equipmentRepository.save(new server.domain.Equipment(equipment.getName(), equipment.getTemperature()));
        takeSnapshot(equipment);
    }

    public Collection<EquipmentData> list() {
        final Collection<EquipmentData> equipments = new LinkedList<>();
        equipmentRepository.findAll().forEach((equipment -> equipments.add(EquipmentData.from(equipment))));
        return equipments;
    }

    public EquipmentData list(String name) {
        return equipmentRepository.findById(name).map(EquipmentData::from).get();
    }

    private void takeSnapshot(EquipmentData equipment) {
        Collection<Float> snapshot = snapshots.get(equipment.getName());
        if (snapshot == null) {
            snapshot = new LinkedList<>();
            snapshots.put(equipment.getName(), snapshot);
        }
        snapshot.add(equipment.getTemperature());
    }
}
