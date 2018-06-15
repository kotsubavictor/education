package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.data.ReleData;
import server.domain.Rele;
import server.repository.ReleRepository;

import java.util.Collection;
import java.util.LinkedList;

@Component
@Scope(value = "singleton")
public class ReleDataService {

    @Autowired
    private ReleRepository releRepository;

    public void save(ReleData equipment) {
        releRepository.save(new Rele(equipment.getName(), equipment.getPower()));
    }

    public Collection<ReleData> list() {
        final Collection<ReleData> equipments = new LinkedList<>();
        releRepository.findAll().forEach((equipment -> equipments.add(ReleData.from(equipment))));
        return equipments;
    }
}
