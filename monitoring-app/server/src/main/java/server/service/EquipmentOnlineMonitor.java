package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.TemperatureSnapshotData;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope(value = "singleton")
public class EquipmentOnlineMonitor {

    @Autowired
    private EquipmentDataService equipmentService;

    @Autowired
    private SnapshotService snapshotService;

    @Scheduled(fixedRate = 300000)
    public void tick() {
        Set<String> onlineEquipment = snapshotService.flush().stream()
                .map(TemperatureSnapshotData.Temperature::getName).collect(Collectors.toSet());

        equipmentService.list().forEach(equipment -> {
            if (!onlineEquipment.contains(equipment.getName())) {
                equipment.setOnline(false);
                equipmentService.save(equipment);
            }
        });

        snapshotService.clear();
    }
}
