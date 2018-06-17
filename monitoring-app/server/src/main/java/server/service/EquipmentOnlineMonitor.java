package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = "singleton")
public class EquipmentOnlineMonitor {

    @Autowired
    private EquipmentDataService equipmentService;

    @Autowired
    private AlertService alertService;

    private Set<String> online = ConcurrentHashMap.newKeySet();

    public void record(EquipmentData equipment) {
        online.add(equipment.getName());
    }

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void flush() {
        equipmentService.list().forEach(equipment -> {
            if (!online.contains(equipment.getName())) {
                equipment.setOnline(false);
                equipmentService.save(equipment);
                alertService.validate(equipment);
            }
        });

        online.clear();
    }
}
