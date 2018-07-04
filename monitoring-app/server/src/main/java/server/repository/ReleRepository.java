package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.domain.Rele;

@Repository
public interface ReleRepository extends CrudRepository<Rele, String> {
}
