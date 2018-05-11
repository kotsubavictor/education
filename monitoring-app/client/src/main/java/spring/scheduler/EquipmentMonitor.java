package spring.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.data.Equipment;

@Component
public class EquipmentMonitor {

    @Value("${rest.equipment.endpoint}")
    private String endpoint;

    @Value("${equipment.name}")
    private String name;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        Equipment equipment = getEquipmentInfo();
        restTemplate.put(endpoint, equipment, equipment.getName());
    }

    private Equipment getEquipmentInfo() {
        return new Equipment(name,  Math.round(Math.random()*20) + 30);
    }
}
