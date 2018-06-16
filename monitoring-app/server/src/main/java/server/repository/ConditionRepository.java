package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.Condition;

@Repository
public interface ConditionRepository extends CrudRepository<Condition, String> {
}