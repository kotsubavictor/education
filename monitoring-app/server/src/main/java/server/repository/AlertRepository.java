package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.Alert;
import server.domain.Condition;

@Repository
public interface AlertRepository extends CrudRepository<Alert, String> {
}