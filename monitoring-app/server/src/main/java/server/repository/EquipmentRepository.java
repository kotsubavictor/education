package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.Equipment;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, String> {
}
