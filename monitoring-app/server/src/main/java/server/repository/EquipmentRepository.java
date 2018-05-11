package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.domain.Equipment;

public interface EquipmentRepository extends CrudRepository<Equipment, String> {

}
