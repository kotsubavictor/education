package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.Equipment;
import server.domain.SnapshotPK;
import server.domain.TemperatureSnapshot;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface TemperatureSnapshotRepository extends CrudRepository<TemperatureSnapshot, SnapshotPK> {
    public List<TemperatureSnapshot> getAllBySnapshotPK_DateAfterAndSnapshotPK_DateBefore(Date after, Date before);
}
