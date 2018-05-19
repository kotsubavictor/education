package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.Equipment;
import server.domain.SnapshotPK;
import server.domain.TemperatureSnapshot;
import server.repository.EquipmentRepository;
import server.repository.TemperatureSnapshotRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = "singleton")
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private TemperatureSnapshotRepository temperatureSnapshotRepository;

    private Map<String, Collection<Float>> snapshots = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 300000)
    public void saveSnapshots() {
        final Date date = new Date();
        snapshots.entrySet().forEach(entry -> {
            TemperatureSnapshot snapshot = new TemperatureSnapshot(entry.getKey(), date);
            snapshot.setMin(0f);
            snapshot.setMax(0f);
            Collection<Float> temperatures = entry.getValue();
            float sum = 0;

            for (Float temperature : temperatures) {
                snapshot.setMin(Math.min(snapshot.getMin(), temperature));
                snapshot.setMax(Math.max(snapshot.getMax(), temperature));
                sum += temperature;
            }

            snapshot.setAverage(sum / temperatures.size());
            temperatureSnapshotRepository.save(snapshot);
        });
        snapshots.clear();
    }

    public void save(Equipment equipment) {
        equipmentRepository.save(new server.domain.Equipment(equipment.getName(), equipment.getTemperature()));
        takeSnapshot(equipment);
    }

    public Collection<Equipment> list() {
        final Collection<Equipment> equipments = new LinkedList<>();
        equipmentRepository.findAll().forEach((equipment -> equipments.add(Equipment.from(equipment))));
        return equipments;
    }

    public Equipment list(String name) {
        return equipmentRepository.findById(name).map(Equipment::from).get();
    }

    private void takeSnapshot(Equipment equipment) {
        Collection<Float> snapshot = snapshots.get(equipment.getName());
        if (snapshot == null) {
            snapshot = new LinkedList<>();
        }
        snapshot.add(equipment.getTemperature());
    }
}
