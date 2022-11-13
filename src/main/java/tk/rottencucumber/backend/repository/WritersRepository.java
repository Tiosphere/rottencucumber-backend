package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.WritersModel;

@Repository
public interface WritersRepository extends CrudRepository<WritersModel, Long> {
}
