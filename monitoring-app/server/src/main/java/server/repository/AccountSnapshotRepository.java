package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.AccountSnapshot;
import server.domain.Alert;
import server.domain.TemperatureSnapshot;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountSnapshotRepository extends CrudRepository<AccountSnapshot, Long> {
    public List<AccountSnapshot> getAllByDateAfterOrderByDateDesc(Date after);
}