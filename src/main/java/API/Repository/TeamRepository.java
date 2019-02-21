package API.Repository;

import API.Models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
}
