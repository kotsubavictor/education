package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.data.EquipmentData;
import server.repository.EquipmentRepository;

import java.util.Collection;
import java.util.LinkedList;

@Component
@Scope(value = "singleton")
public class EquipmentDataService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public void save(EquipmentData equipment) {
        equipmentRepository.save(new server.domain.Equipment(equipment.getName(), equipment.getOnline(), equipment.getTemperature(), equipment.getHumidity()));
    }

    public Collection<EquipmentData> list() {
        final Collection<EquipmentData> equipments = new LinkedList<>();
        equipmentRepository.findAll().forEach((equipment -> equipments.add(EquipmentData.from(equipment))));
        return equipments;
    }

    public EquipmentData list(String name) {
        return equipmentRepository.findById(name).map(EquipmentData::from).get();
    }
}
