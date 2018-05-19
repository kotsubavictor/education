package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.data.TemperatureSnapshotData;
import server.domain.TemperatureSnapshot;
import server.repository.EquipmentRepository;
import server.repository.TemperatureSnapshotRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Scope(value = "singleton")
public class EquipmentService {

    private static final String ALERT_MESSAGE = "ALERT - TEMPERATURE";

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private TemperatureSnapshotRepository temperatureSnapshotRepository;

    @Autowired
    private PushService pushService;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${server.mail.to}")
    private String mailTo;

    @Value("${server.alert.high.temp}")
    private int alertHightTemp;

    @Value("${server.alert.low.temp}")
    private int alertLowtTemp;

    private Map<String, Collection<Float>> snapshots = new ConcurrentHashMap<>();
    private Map<String, String> alerts = new ConcurrentHashMap<>();

    @PostConstruct
    public void initDefaultSnapshots() {
        equipmentRepository.findAll().forEach(
                equipment -> snapshots.put(equipment.getName(), new LinkedList<>())
        );
        alerts.clear();
    }

    @Scheduled(fixedRate = 300000)
    public void saveSnapshots() {
        final Date date = new Date();
        final Collection<TemperatureSnapshot> data = new LinkedList<>();
        snapshots.entrySet().forEach(entry -> {
            TemperatureSnapshot snapshot = new TemperatureSnapshot(entry.getKey(), date);
            snapshot.setMin(100f);
            snapshot.setMax(0f);
            snapshot.setAverage(0f);
            Collection<Float> temperatures = entry.getValue();

            if (!temperatures.isEmpty()) {
                float sum = 0;
                for (Float temperature : temperatures) {
                    snapshot.setMin(Math.min(snapshot.getMin(), temperature));
                    snapshot.setMax(Math.max(snapshot.getMax(), temperature));
                    sum += temperature;
                }

                snapshot.setAverage(sum / temperatures.size());
                temperatureSnapshotRepository.save(snapshot);
            }
            validateTemperature(snapshot.getSnapshotPK().getName(), snapshot.getMax());
            data.add(snapshot);
        });

        initDefaultSnapshots();

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
        validateTemperature(equipment.getName(), equipment.getTemperature());
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

    private void validateTemperature(String name, Float temperature) {
        if ((temperature > alertHightTemp || temperature < alertLowtTemp) && !alerts.containsKey(name)) {
            alerts.put(name, name);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailTo);
            message.setSubject(ALERT_MESSAGE);
            message.setText(name.toUpperCase() + ": " + temperature);
            emailSender.send(message);
        }
    }
}
