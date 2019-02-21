package API.Repository;

import API.Models.Footballer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface FootballerRepository extends CrudRepository<Footballer, Long> {

}
