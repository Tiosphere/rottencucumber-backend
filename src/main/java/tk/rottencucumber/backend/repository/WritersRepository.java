package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.WritersModel;

public interface WritersRepository extends CrudRepository<WritersModel, Long> {
}
