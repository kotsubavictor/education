package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.Equipment;
import server.domain.SnapshotPK;
import server.domain.TemperatureSnapshot;

@Repository
public interface TemperatureSnapshotRepository extends CrudRepository<TemperatureSnapshot, SnapshotPK> {
}
